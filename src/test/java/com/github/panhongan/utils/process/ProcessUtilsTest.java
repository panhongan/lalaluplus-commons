package com.github.panhongan.utils.process;

/**
 * @author lalalu plus
 * @since 2019.8.3
 */

public class ProcessUtilsTest {

	public static void main(String[] args) {
		for (int i = 0; i < 100; i++) {
			long begin = System.currentTimeMillis();
			System.out.println(ProcessUtils.getPid() + ", " + (System.currentTimeMillis() - begin));
		}

		for (int i = 0; i < 100; i++) {
			long begin = System.currentTimeMillis();
			System.out.println(ProcessUtils.getJavaCmd() + ", " + (System.currentTimeMillis() - begin));
		}

		System.out.println(ProcessUtils.getMainName());
		System.out.println(ProcessUtils.getCmdOptions());
	}
}
