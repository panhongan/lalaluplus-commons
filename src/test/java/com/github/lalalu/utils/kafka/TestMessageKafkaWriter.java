package com.github.lalalu.utils.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.lalalu.utils.conf.Config;

/**
 * lalalu plus
 */
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
		
		String src_topic = "test";
		String dst_topic = "test-reformat";
		Config producer_config = new Config();
		producer_config.addProperty("bootstrap.servers", config.getString("dst.kafka.broker.list"));
		producer_config.addProperty("metadata.broker.list", config.getString("dst.kafka.broker.list"));
		producer_config.addProperty("zookeeper.connect", config.getString("dst.kafka.zk.list"));
		producer_config.addProperty("producer.type", "async");
		MessageKafkaWriter kafka_writer = new MessageKafkaWriter(producer_config, dst_topic, null);
		if (kafka_writer.init()) {
			logger.info("KafkaWriter init ok");
			
			for (int i = 0; i < 120; ++i) {
				kafka_writer.processMessage(src_topic, 1, new Integer(i).toString());
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
