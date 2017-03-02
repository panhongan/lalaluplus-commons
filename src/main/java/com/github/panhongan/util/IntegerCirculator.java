package com.github.panhongan.util;

public class IntegerCirculator {
	
	private int range = 0;
	
	private int count = 0;
	
	public IntegerCirculator(int range) throws Exception {
		if (range <= 0) {
			throw new Exception("invalid range, should be > 0");
		}
		
		this.range = range;
	}
	
	public int incrementAndGet() {
		if (++count == range) {
			count = 0;
		}
		return count;
	}
	
	public void increment() {
		if (++count == range) {
			count = 0;
		}
	}
	
	public void decrement() {
		if (--count == -1) {
			count = range - 1;
		}
	}

	public int decrementAndGet() {
		if (--count == -1) {
			count = range - 1;
		}
		return count;
	}
	
	public int get() {
		return count;
	}
	
	public void reset() {
		count = 0;
	}

}
