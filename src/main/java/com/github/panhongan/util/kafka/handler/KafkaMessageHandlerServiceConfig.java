package com.github.panhongan.util.kafka.handler;

import com.github.panhongan.util.StringUtil;
import com.github.panhongan.util.conf.Config;

public class KafkaMessageHandlerServiceConfig {

	private Config config = new Config();
	
	public boolean parse(String confFile) {
		return config.parse(confFile);
	}
	
	public Config getConfig() {
		return config;
	}
	
	public boolean isValid() {
		return (!StringUtil.isEmpty(config.getString("src.kafka.zk.list")) &&
				!StringUtil.isEmpty(config.getString("src.kafka.broker.list")) &&
				!StringUtil.isEmpty(config.getString("src.kafka.consumer.group")) &&
				!StringUtil.isEmpty(config.getString("src.kafka.topic.partition")) &&
				!StringUtil.isEmpty(config.getString("src.kafka.consumer.restart.offset.largest")) &&
				!StringUtil.isEmpty(config.getString("dst.kafka.zk.list")) &&
				!StringUtil.isEmpty(config.getString("dst.kafka.broker.list")) &&
				!StringUtil.isEmpty(config.getString("inner.queue.num")) &&
				!StringUtil.isEmpty(config.getString("inner.processors.per.queue")));
	}
	
	@Override
	public String toString() {
		return config.toString();
	}
	
}
