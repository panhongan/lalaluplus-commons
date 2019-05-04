package com.github.lalalu.utils.kafka;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.lalalu.utils.conf.Config;

/**
 * lalalu plus
 */
public class TestMessageLocalWriterGroup {

	private static final Logger logger = LoggerFactory.getLogger(TestMessageLocalWriterGroup.class);

	public static void main(String[] args) {
		// local.data.dir, zk.list, kafka.consumer.group
		String conf_file = "../conf/kafka.properties";
		Config config = new Config();
		if (!config.parse(conf_file)) {
			logger.warn("parse conf file failed : {}", conf_file);
			return;
		}

		logger.info(config.toString());

		String topic = "test";
		int partitions = 4;

		List<AbstractKafkaMessageProcessor> processors = new ArrayList<AbstractKafkaMessageProcessor>();
		for (int i = 0; i < partitions; ++i) {
			MessageLocalWriter local_writer = new MessageLocalWriter(config.getString("local.data.dir"), 10);
			if (local_writer.init()) {
				processors.add(local_writer);
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
			Thread.sleep(5 * 60 * 1000);
		} catch (Exception e) {
		}

		// uninit
		group.uninit();

		for (AbstractKafkaMessageProcessor processor : processors) {
			processor.uninit();
		}
	}

}
