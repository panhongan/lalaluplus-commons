package com.github.panhongan.util.kafka.handler;

import com.github.panhongan.util.StringUtil;
import com.github.panhongan.util.conf.Config;
import org.apache.commons.lang3.StringUtils;

public class KafkaMessageServiceConfig {

	private Config config = new Config();
	
	public boolean parse(String confFile) {
		return config.parse(confFile);
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
