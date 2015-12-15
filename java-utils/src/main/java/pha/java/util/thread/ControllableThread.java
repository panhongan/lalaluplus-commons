package pha.java.util.thread;

import org.apache.log4j.Logger;

import pha.java.util.control.Freezable;
import pha.java.util.control.Lifecycleable;


public abstract class ControllableThread extends Thread implements Lifecycleable, Freezable {
	
	private static Logger logger = Logger.getLogger(ControllableThread.class);
	
	private boolean is_finished = false;
	
	private boolean is_frozen = false;
	
	private int sleep_interval = 1000;	// millionseconds
	
	public ControllableThread() {
		
	}
	
	public ControllableThread(boolean is_frozen) {
		this.is_frozen = is_frozen;
	}
	
	public void setSleepInterval(int sleep_interval) {
		if (sleep_interval < 0) {
			this.sleep_interval = 1000;
		} else {
			this.sleep_interval = sleep_interval;
		}
	}
	
	@Override
	public void run() {
		while (!is_finished) {
			if (!is_frozen) {
				this.work();
			}
			
			// sleep
			try {
				Thread.sleep(sleep_interval);
			} catch (Exception e) {
				logger.warn(e.getMessage());
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
	public void defreeze() {
		this.is_frozen = false;
	}
	
	protected abstract void work();

}
