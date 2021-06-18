package com.github.panhongan.utils.throttling;

import org.junit.Test;

/**
 * @author lalalu plus
 * @since 2019.7.1
 */

public class ThrottlingTest {

    @Test
    public void testThrottling() {
        ThrottlingCounter throttlingCounter = new ThrottlingCounter("counter1", 10);
        System.out.println(throttlingCounter.toString());

        for (int i = 0; i < throttlingCounter.getThreshold() + 1; ++i) {
            System.out.println(i + " : " + throttlingCounter.tryEnter());
        }
    }
}
