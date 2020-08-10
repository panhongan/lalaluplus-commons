package com.github.panhongan.utils.kafka.handler;

import com.github.panhongan.utils.conf.Config;
import org.apache.commons.lang3.StringUtils;

/**
 * lalalu plus
 */
public class KafkaMessageServiceConfig {

	private Config config = new Config();

	public boolean parse(String confFile) {
		config.parse(confFile);
		return true;
	}

	public Config getConfig() {
		return config;
	}

	public boolean isValid() {
		return (StringUtils.isNotEmpty(config.getString("src.kafka.zk.list")) &&
				StringUtils.isNotEmpty(config.getString("src.kafka.broker.list")) &&
				StringUtils.isNotEmpty(config.getString("src.kafka.consumer.group")) &&
				StringUtils.isNotEmpty(config.getString("src.kafka.topic.partition")) &&
				StringUtils.isNotEmpty(config.getString("src.kafka.consumer.restart.offset.largest")) &&
				StringUtils.isNotEmpty(config.getString("dst.kafka.zk.list")) &&
				StringUtils.isNotEmpty(config.getString("dst.kafka.broker.list")) &&
				StringUtils.isNotEmpty(config.getString("inner.queue.num")) &&
				StringUtils.isNotEmpty(config.getString("inner.processors.per.queue")));
	}

	@Override
	public String toString() {
		return config.toString();
	}

}
