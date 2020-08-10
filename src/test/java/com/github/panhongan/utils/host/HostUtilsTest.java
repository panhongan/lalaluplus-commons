package com.github.panhongan.utils.host;

import org.junit.Test;

/**
 * @author lalalu plus
 * @since 2017.7.10
 * @version 1.0
 */

public class HostUtilsTest {

    @Test
	public void testGetHostName_Ok() {
        for (int i = 0; i < 100; i++) {
            long begin = System.currentTimeMillis();
            System.out.println(HostUtils.getHostName() + ", " + (System.currentTimeMillis() - begin));
        }
    }

    @Test
    public void testGetIp_Ok() {
		for (int i = 0; i < 100; i++) {
			long begin = System.currentTimeMillis();
			System.out.println(HostUtils.getIp() + ", " + (System.currentTimeMillis() - begin));
		}
	}
}
