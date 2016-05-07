package com.github.panhongan.util.mq.kafka;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.panhongan.util.control.Lifecycleable;

import kafka.javaapi.producer.Producer;


public class MessageKafkaWriter extends AbstractMessageProcessor implements Lifecycleable {
	
	private static final Logger logger = LoggerFactory.getLogger(MessageKafkaWriter.class);
	
	private String zk_list = null;
	
	private String broker_list = null;
	
	private Producer<String, String> producer = null;
	
	/**
	 * @param zk_list
	 * @param broker_list
	 */
	
	public MessageKafkaWriter(String zk_list, String broker_list) {
		this.zk_list = zk_list;
		this.broker_list = broker_list;
	}
	
	@Override
	public boolean init() {
		producer = KafkaUtil.createProducer(zk_list, broker_list, false);
		return (producer != null);
	}
	
	@Override
	public void uninit() {
		KafkaUtil.closeProducer(producer);
	}

	@Override
	public Object processMessage(String topic, int partition_id, String message){
		int ret = KafkaUtil.sendData(producer, topic, message);
		if (ret == 1) {
			logger.warn("send message to kafka failed : {}", message);
			KafkaUtil.closeProducer(producer);
			
			producer = KafkaUtil.createProducer(zk_list, broker_list, false);
			ret = KafkaUtil.sendData(producer, topic, message);
		}

		return (ret == 0);
	}
	
	@Override
	public Object processMessage(String topic, int partition_id, List<String> msg_list) {
		int ret = KafkaUtil.sendBatch(producer, topic, msg_list);
		if (ret == 1) {
			logger.warn("send message to kafka failed");
			KafkaUtil.closeProducer(producer);
			
			producer = KafkaUtil.createProducer(zk_list, broker_list, true);
			ret = KafkaUtil.sendBatch(producer, topic, msg_list);
		}
		
		return (ret == 0);
	}

}
