package com.github.panhongan.utils.thread;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * lalalu plus
 */

public class ThreadManager {

	private static final Logger logger = LoggerFactory.getLogger(ThreadManager.class);

	private List<Thread> threads = new ArrayList<>();

	public void addThread(Thread t) {
		if (t != null) {
			threads.add(t);
			t.start();
		}
	}

	public void waitForCompletion() {
		for (Thread t : threads) {
			try {
				t.join();
				logger.info("thread finished : {}", t.getName());
			} catch (Exception e) {
				logger.warn(e.getMessage());
			}
		}
	}
}
