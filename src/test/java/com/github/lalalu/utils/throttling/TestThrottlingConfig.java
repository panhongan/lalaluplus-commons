package com.github.lalalu.utils.throttling;

/**
 * lalalu plus
 */

public class TestThrottlingConfig {

	public static void main(String[] args) {
		ThrottlingConfig throttlingConfig = new ThrottlingConfig("config1", 10);
		System.out.println(throttlingConfig.toString());

		for (int i = 0; i < throttlingConfig.getThreshold() + 1; ++i) {
			System.out.println(i + " : " + throttlingConfig.tryEnter());
		}
	}

}
