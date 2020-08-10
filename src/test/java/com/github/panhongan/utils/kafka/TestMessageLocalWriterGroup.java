package com.github.panhongan.utils.kafka;

import java.util.ArrayList;
import java.util.List;

import com.github.panhongan.utils.conf.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * lalalu plus
 */
public class TestMessageLocalWriterGroup {

	private static final Logger logger = LoggerFactory.getLogger(TestMessageLocalWriterGroup.class);

	public static void main(String[] args) {
		// local.data.dir, zk.list, kafka.consumer.group
		String conf_file = "../conf/kafka.properties";
		Config config = new Config();
		config.parse(conf_file);

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
