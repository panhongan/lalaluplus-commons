package com.github.panhongan.util.kafka.handler;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.panhongan.util.StringUtil;
import com.github.panhongan.util.conf.Config;
import com.github.panhongan.util.control.Lifecycleable;
import com.github.panhongan.util.kafka.AbstractKafkaMessageHandler;
import com.github.panhongan.util.kafka.HighLevelConsumerGroup;
import com.github.panhongan.util.kafka.MessageKafkaWriter;

public class KafkaMessageHandlerService implements Lifecycleable {

	private static final Logger logger = LoggerFactory.getLogger(KafkaMessageHandlerService.class);

	private List<AbstractKafkaMessageHandler> workflows = new ArrayList<AbstractKafkaMessageHandler>();
	
	private KafkaMessageHandlerServiceConfig config = null;

	private HighLevelConsumerGroup group = null;
	
	private MessageKafkaWriter kafka_writer = null;
	
	private AbstractMessageHandler handler = null;
	
	/**
	 * @param converter should invoke init() succeed
	 */
	public void setMessageHandler(AbstractMessageHandler handler) {
		this.handler = handler;
	}
	
	public void setConfig(KafkaMessageHandlerServiceConfig config) {
		this.config = config;
	}

	@Override
	public boolean init() {
		boolean is_ok = config.isValid();
		if (!is_ok) {
			logger.warn("KafkaMessageHandlerServiceConfig is invalid : {}" + config.toString());
			return is_ok;
		}

		Config conf = config.getConfig();
		
		Config producer_config = new Config();
		producer_config.addProperty("bootstrap.servers", conf.getString("dst.kafka.broker.list"));
		producer_config.addProperty("zookeeper.connect", conf.getString("dst.kafka.zk.list"));
		kafka_writer = new MessageKafkaWriter(producer_config, null);
		is_ok = kafka_writer.init();
		if (!is_ok) {
			logger.warn("MessageKafkaWriter init failed");
			return is_ok;
		}

		String topic_partition = conf.getString("src.kafka.topic.partition");
		if (topic_partition == null) {
			logger.warn("property not exist : {}", "src.kafka.topic.partition");
			return is_ok;
		}
		
		String[] arr = topic_partition.split(":");
		if (arr != null && arr.length == 2) {
			String topic = arr[0];
			int partitions = Integer.valueOf(arr[1]);

			for (int i = 0; i < partitions; ++i) {
				KafkaMessageWorkflow workflow = new KafkaMessageWorkflow(handler, kafka_writer, config);
				workflow.setName(workflow.getClass().getSimpleName() + "_" + topic + "_" + i);
				if (workflow.init()) {
					workflows.add(workflow);
				} else {
					logger.warn(workflow.getName() + " init failed");
					return false;
				}
			}

			is_ok = (workflows.size() == partitions);
			if (!is_ok) {
				logger.warn("init KafkaMessageWorkflow failed");
				return false;
			}

			group = new HighLevelConsumerGroup(conf.getString("src.kafka.zk.list"),
					conf.getString("src.kafka.consumer.group"), topic, partitions,
					conf.getBoolean("src.kafka.consumer.restart.offset.largest", false), 
					workflows);
			is_ok = group.init();
			if (is_ok) {
				logger.info("HighLevelConsumerGroup init ok");
			} else {
				logger.warn("HighLevelConsumerGroup init failed");
			}
		}

		return is_ok;
	}

	@Override
	public void uninit() {
		if (group != null) {
			group.uninit();
			logger.info("HighLevelConsumerGroup uninit");
		}

		for (AbstractKafkaMessageHandler processor : workflows) {
			processor.uninit();
			logger.info("MessageProcessor uninit : {}", StringUtil.toString(processor.getName()));
		}
		
		if (kafka_writer != null) {
			kafka_writer.uninit();
			logger.info("MessageKafkaWriter uninit");
		}
	}
	
}
