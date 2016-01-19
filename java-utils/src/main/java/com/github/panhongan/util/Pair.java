package com.github.panhongan.util;

public class Pair<First, Second> {

	public First first;
	
	public Second second;
	
	public Pair(First first, Second second) {
		this.first = first;
		this.second = second;
	}
	
	@Override
	public String toString() {
		return "Pair(" + StringUtil.toString(first) + "," + StringUtil.toString(second) + ")";
	}
	
}
