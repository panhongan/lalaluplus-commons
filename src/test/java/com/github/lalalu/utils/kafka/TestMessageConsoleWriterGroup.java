package com.github.lalalu.utils.kafka;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.lalalu.utils.conf.Config;

/**
 * lalalu plus
 */
public class TestMessageConsoleWriterGroup {
	
	private static final Logger logger = LoggerFactory.getLogger(TestMessageConsoleWriterGroup.class);
	
	public static void main(String [] args) {
		// zk.list, kafka.consumer.group
		String conf_file = "../conf/kafka.properties";
		Config config = new Config();
		if (!config.parse(conf_file)) {
			logger.warn("parse conf file failed : {}", conf_file);
			return;
		}
		
		logger.info(config.toString());
		
		String topic = "test";
		int partitions = 4;
		
		// console writer
		List<AbstractKafkaMessageProcessor> processors = new ArrayList<AbstractKafkaMessageProcessor>();
		for (int i = 0; i < partitions; ++i) {
			MessageConsoleWriter console_writer = new MessageConsoleWriter();
			if (console_writer.init()) {
				processors.add(console_writer);
			}
		}
		
		HighLevelConsumerGroup group = new HighLevelConsumerGroup(config.getString("zk.list"), 
				config.getString("kafka.consumer.group"), 
				topic, partitions, true, processors);
		
		if (group.init()) {
			logger.info("HighLevelConsumerGroup init ok");
		} else {
			logger.warn("HighLevelConsumerGroup init failed");
		}
		
		try {
			Thread.sleep(60 * 1000);
		} catch (Exception e) {
		}
		
		// uninit
		group.uninit();
		
		for (AbstractKafkaMessageProcessor processor : processors) {
			processor.uninit();
		}
	}

}
