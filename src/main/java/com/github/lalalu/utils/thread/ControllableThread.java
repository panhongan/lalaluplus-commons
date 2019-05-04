package com.github.lalalu.utils.thread;


import com.github.lalalu.utils.control.Freezable;
import com.github.lalalu.utils.control.Lifecycleable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.lalalu.utils.control.Freezable;
import com.github.lalalu.utils.control.Lifecycleable;

/**
 * lalalu plus
 */
public abstract class ControllableThread extends Thread implements Lifecycleable, Freezable {

	private static final Logger logger = LoggerFactory.getLogger(ControllableThread.class);

	private boolean is_finished = false;

	private boolean is_frozen = false;

	private int sleep_interval = 1000;    // millionseconds

	public ControllableThread() {

	}

	public ControllableThread(boolean is_frozen) {
		this.is_frozen = is_frozen;
	}

	public void setSleepInterval(int sleep_interval) {
		this.sleep_interval = sleep_interval;
	}

	@Override
	public void run() {
		while (!is_finished) {
			if (!is_frozen) {
				this.work();
			}

			// sleep
			if (this.sleep_interval > 0) {
				try {
					Thread.sleep(sleep_interval);
				} catch (Exception e) {
					logger.warn(e.getMessage());
				}
			}
		}
	}

	@Override
	public boolean init() {
		this.start();
		return true;
	}

	@Override
	public void uninit() {
		is_finished = true;

		if (this.getState().equals(State.TIMED_WAITING)) {
			try {
				this.interrupt();
			} catch (Exception e) {
				logger.warn(e.getMessage());
			}
		}

		try {
			this.join();
		} catch (Exception e) {
			logger.warn(e.getMessage());
		}
	}

	@Override
	public void freeze() {
		this.is_frozen = true;
	}

	@Override
	public void unfreeze() {
		this.is_frozen = false;
	}

	protected abstract void work();

}
