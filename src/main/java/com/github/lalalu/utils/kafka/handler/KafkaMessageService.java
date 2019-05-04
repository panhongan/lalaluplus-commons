package com.github.lalalu.utils.kafka.handler;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.lalalu.utils.conf.Config;
import com.github.lalalu.utils.control.Lifecycleable;
import com.github.lalalu.utils.kafka.AbstractKafkaMessageProcessor;
import com.github.lalalu.utils.kafka.HighLevelConsumerGroup;
import com.github.lalalu.utils.kafka.MessageKafkaWriter;

/**
 * lalalu plus
 */
public class KafkaMessageService implements Lifecycleable {

	private static final Logger logger = LoggerFactory.getLogger(KafkaMessageService.class);

	private List<AbstractKafkaMessageProcessor> workFlows = new ArrayList<>();
	
	private KafkaMessageServiceConfig config = null;

	private HighLevelConsumerGroup group = null;
	
	private MessageKafkaWriter kafka_writer = null;
	
	private AbstractMessageHandler handler = null;
	
	/**
	 * @param handler should invoke init() succeed
	 */
	public void setMessageHandler(AbstractMessageHandler handler) {
		this.handler = handler;
	}
	
	public void setConfig(KafkaMessageServiceConfig config) {
		this.config = config;
	}

	@Override
	public boolean init() {
		boolean is_ok = config.isValid();
		if (!is_ok) {
			logger.warn("KafkaMessageServiceConfig is invalid : {}" + config.toString());
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
					workFlows.add(workflow);
				} else {
					logger.warn(workflow.getName() + " init failed");
					return false;
				}
			}

			is_ok = (workFlows.size() == partitions);
			if (!is_ok) {
				logger.warn("init KafkaMessageWorkflow failed");
				return false;
			}

			group = new HighLevelConsumerGroup(conf.getString("src.kafka.zk.list"),
					conf.getString("src.kafka.consumer.group"), topic, partitions,
					conf.getBoolean("src.kafka.consumer.restart.offset.largest", false), 
					workFlows);
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

		for (AbstractKafkaMessageProcessor processor : workFlows) {
			processor.uninit();
			logger.info("MessageProcessor uninit : {}", processor.getName());
		}
		
		if (kafka_writer != null) {
			kafka_writer.uninit();
			logger.info("MessageKafkaWriter uninit");
		}
		
		if (handler != null) {
			handler.uninit();
		}
	}
	
}
