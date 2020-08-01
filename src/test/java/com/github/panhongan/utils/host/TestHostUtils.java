package com.github.panhongan.utils.host;

/**
 * lalalu plus
 */

public class TestHostUtils {

	public static void main(String[] args) {
		for (int i = 0; i < 100; i++) {
			long begin = System.currentTimeMillis();
			System.out.println(HostUtils.getHostName() + ", " + (System.currentTimeMillis() - begin));
		}

		for (int i = 0; i < 100; i++) {
			long begin = System.currentTimeMillis();
			System.out.println(HostUtils.getIp() + ", " + (System.currentTimeMillis() - begin));
		}
	}

}
