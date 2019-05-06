package com.github.lalalu.utils.throttling;

import java.util.concurrent.atomic.AtomicInteger;

import com.google.common.base.Preconditions;

/**
 * lalalu plus
 */

public class ThrottlingConfig {

	private final String configName;

	private final int threshold;

	private volatile AtomicInteger count = new AtomicInteger(0);

	public ThrottlingConfig(String configName, int threshold) {
		this.configName = configName;
		this.threshold = threshold;
		Preconditions.checkNotNull(configName);
		Preconditions.checkArgument(threshold > 0);
	}

	public String getConfigName() {
		return configName;
	}

	public int getThreshold() {
		return threshold;
	}

	public boolean tryEnter() {
		if (count.incrementAndGet() > threshold) {
			return false;
		} else {
			return true;
		}
	}

	public void leave() {
		count.decrementAndGet();
	}

	@Override
	public String toString() {
		return "(" + configName + ", " + threshold + ")";
	}

}
