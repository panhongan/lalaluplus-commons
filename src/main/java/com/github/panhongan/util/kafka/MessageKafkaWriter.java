package com.github.panhongan.util.kafka;

import java.util.List;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.Producer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.panhongan.util.StringUtil;
import com.github.panhongan.util.conf.Config;
import com.github.panhongan.util.control.Lifecycleable;

public class MessageKafkaWriter extends AbstractMessageProcessor implements Lifecycleable {
	
	private static final Logger logger = LoggerFactory.getLogger(MessageKafkaWriter.class);
	
	private Config producer_config = null;
	
	private Producer<String, String> producer = null;
	
	private MessageLocalWriter local_writer = null;
	
	private boolean sync = false;
	
	public MessageKafkaWriter(Config producer_config, String send_failed_data_dir) {
		this.producer_config = producer_config;
		this.sync = producer_config.getString("producer.type", "async").contentEquals("sync");
		
		if (!StringUtil.isEmpty(send_failed_data_dir)) {
			local_writer = new MessageLocalWriter(send_failed_data_dir, 1);
		}
	}
	
	@Override
	public boolean init() {
		boolean is_ok = false;
		
		is_ok = KafkaUtil.isKafkaClusterAlive(producer_config.getString("zookeeper.connect"));
		if (!is_ok) {
			logger.warn("Kafka cluster is not alive");
			return is_ok;
		}
		
		logger.info("Kafka cluster is alive");
		
		try {
			producer = KafkaUtil.createProducer(producer_config);
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
		}
		
		return (producer != null);
	}
	
	@Override
	public void uninit() {
		KafkaUtil.closeProducer(producer);
		
		if (local_writer != null) {
			local_writer.uninit();
		}
	}

	@Override
	public Object processMessage(String topic, int partition_id, String message){
		if (sync) {
			if (!KafkaUtil.sendSync(producer, topic, message)) {
				logger.warn("send message to kafka failed : {}", message);
				
				// 写入本地文件
				if (local_writer != null) {
					local_writer.processMessage(topic, partition_id, message);
				}
			}
		} else {
			Callback callback = null;
			if (local_writer != null) {
				callback = new KafkaProducerSendFailedCallback(local_writer, topic, partition_id, message);
			}
			KafkaUtil.sendAsync(producer, topic, message, callback);
		}

		return true;
	}
	
	@Override
	public Object processMessage(String topic, int partition_id, List<String> msg_list) {
		for (String message : msg_list) {
			this.processMessage(topic, partition_id, message);
		}

		return true;
	}
	
}
