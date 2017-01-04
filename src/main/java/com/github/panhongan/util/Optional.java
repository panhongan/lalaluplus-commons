package com.github.panhongan.util;

public class Optional<T> {

	private T t;
	
	public Optional(T t) {
		this.t = t;
	}
	
	public boolean isEmpty() {
		return (t == null);
	}
	
	public T get() {
		return t;
	}
	
	public T getOrElse(T default_val) {
		T ret = t;
		if (isEmpty()) {
			ret = default_val;
		}
		return ret;
	}
	
}
