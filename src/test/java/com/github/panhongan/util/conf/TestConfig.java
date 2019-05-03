package com.github.panhongan.util.conf;

/**
 * lalalu plus
 */

public class TestConfig {

	public static void main(String[] args) {
		Config config = new Config();
		config.parse("conf/hive.properties");
		System.out.println(config.toString());
		System.out.println(config.keySet());
	}

}
