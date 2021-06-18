package com.github.panhongan.utils.throttling;

import com.google.common.base.Preconditions;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Throttling Counter
 *
 * @author lalalu plus
 */

public class ThrottlingCounter {

    private final String counterName;

    private final int threshold;

    private volatile AtomicInteger count = new AtomicInteger(0);

    /**
     * @param counterName Counter name
     * @param threshold Threshold count
     */
    public ThrottlingCounter(String counterName, int threshold) {
        Preconditions.checkNotNull(counterName);
        Preconditions.checkArgument(threshold > 0);

        this.counterName = counterName;
        this.threshold = threshold;
    }

    public String getCounterName() {
        return counterName;
    }

    public int getThreshold() {
        return threshold;
    }

    public boolean tryEnter() {
        return count.incrementAndGet() > threshold;
    }

    public void leave() {
        count.decrementAndGet();
    }

    @Override
    public String toString() {
        return "(" + counterName + ", " + threshold + ")";
    }
}
