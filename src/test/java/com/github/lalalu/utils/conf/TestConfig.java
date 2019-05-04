package com.github.lalalu.utils.conf;

/**
 * lalalu plus
 */

public class TestConfig {

	public static void main(String[] args) {
		Config config = new Config();
		config.parse("conf/hive.properties");
		System.out.println(config.toString());
		System.out.println(config.keySet());

		Config config1 = new Config();
		config1.addProperty("name", "pha");
		config1.addProperty("age", "123");
		config1.addProperty("money", "12345");
		System.out.println(config1.getString("name"));
        System.out.println(config1.getInt("age", 0));
        System.out.println(config1.getLong("money", 0));
    }

}
