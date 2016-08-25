package com.github.panhongan.util.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.panhongan.util.conf.Config;
import com.github.panhongan.util.kafka.MessageKafkaWriter;


public class TestMessageKafkaWriter {
	
	private static final Logger logger = LoggerFactory.getLogger(TestMessageKafkaWriter.class);
	
	public static void main(String [] args) {
		// dst.kafka.zk.list, dst.kafka.broker.list
		String conf_file = "../conf/kafka.properties";
		Config config = new Config();
		if (!config.parse(conf_file)) {
			logger.warn("parse conf file failed : {}", conf_file);
			return;
		}
		
		logger.info(config.toString());
		
		String new_topic = "test-reformat";
		MessageKafkaWriter kafka_writer = new MessageKafkaWriter(config.getString("dst.kafka.zk.list"),
				config.getString("dst.kafka.broker.list"));
		if (kafka_writer.init()) {
			logger.info("KafkaWriter init ok");
			
			for (int i = 0; i < 120; ++i) {
				kafka_writer.processMessage(new_topic, 1, new Integer(i).toString());
				try {
					Thread.sleep(1 * 1000);
				} catch (Exception e) {
					
				}
			}
		} else {
			logger.warn("KafkaWriter init failed");
		}
		
		kafka_writer.uninit();
	}
		
}
