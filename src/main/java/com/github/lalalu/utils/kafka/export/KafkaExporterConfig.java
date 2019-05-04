package com.github.lalalu.utils.kafka.export;

import org.apache.commons.lang3.StringUtils;

import com.github.lalalu.utils.conf.Config;

/**
 * lalalu plus
 */

public class KafkaExporterConfig {

	private static KafkaExporterConfig instance = new KafkaExporterConfig();

	private Config config = new Config();

	public static KafkaExporterConfig getInstance() {
		return instance;
	}

	private KafkaExporterConfig() {
	}

	public boolean parse(String confFile) {
		return config.parse(confFile);
	}

	public Config getConfig() {
		return config;
	}

	public boolean isValid() {
		return (StringUtils.isNotEmpty(config.getString("kafka.zk.list")) &&
				StringUtils.isNotEmpty(config.getString("kafka.broker.list")) &&
				StringUtils.isNotEmpty(config.getString("kafka.consumer.group")) &&
				StringUtils.isNotEmpty(config.getString("kafka.consumer.group.restart.offset.largest")) &&
				StringUtils.isNotEmpty(config.getString("kafka.topic.partition")) &&
				StringUtils.isNotEmpty(config.getString("local.data.dir")) &&
				StringUtils.isNotEmpty(config.getString("local.data.minutes.window")));
	}

	@Override
	public String toString() {
		return config.toString();
	}

}
