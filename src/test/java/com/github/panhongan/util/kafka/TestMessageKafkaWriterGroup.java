package com.github.panhongan.util.kafka;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.panhongan.util.conf.Config;
import com.github.panhongan.util.kafka.AbstractMessageProcessor;
import com.github.panhongan.util.kafka.HighLevelConsumerGroup;
import com.github.panhongan.util.kafka.MessageKafkaWriter;


public class TestMessageKafkaWriterGroup {
	
	private static final Logger logger = LoggerFactory.getLogger(TestMessageKafkaWriterGroup.class);
	
	public static void main(String [] args) {
		// src.kafka.zk.list, src.kafka.consumer.group, src.kafka.broker.list
		// dst.kafka.zk.list, dst.kafka.broker.list
		String conf_file = "../conf/kafka.properties";
		Config config = new Config();
		if (!config.parse(conf_file)) {
			logger.warn("parse conf file failed : {}", conf_file);
			return;
		}
		
		logger.info(config.toString());
		
		String src_topic = "test";
		String dst_topic = "test-kafka";
		int partitions = 4;
		
		Config producer_config = new Config();
		producer_config.addProperty("bootstrap.servers", config.getString("dst.kafka.broker.list"));
		MessageKafkaWriter kafka_writer = new MessageKafkaWriter(producer_config, dst_topic, null);
		if (!kafka_writer.init()) {
			logger.warn("KafkaWriter init failed");
			return;
		}
		
		List<AbstractMessageProcessor> processors = new ArrayList<AbstractMessageProcessor>();
		for (int i = 0; i < partitions; ++i) {
			processors.add(kafka_writer);
		}
		
		HighLevelConsumerGroup group = new HighLevelConsumerGroup(config.getString("src.kafka.zk.list"), 
				config.getString("src.kafka.consumer.group"),
				src_topic, partitions, true, processors);
		if (group.init()) {
			logger.info("HighLevelConsumerGroup init ok");
		} else {
			logger.warn("HighLevelConsumerGroup init failed");
		}
		
		try {
			Thread.sleep(20 * 1000);
		} catch (Exception e) {
		}
		
		// uninit
		group.uninit();
		
		for (AbstractMessageProcessor processor : processors) {
			processor.uninit();
		}
	}

}
