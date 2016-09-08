package com.github.panhongan.util.hash;


public class SimplePartitioner {
	
	public static int partition(String str, int mod) {
		return (SimpleHash.hash(str) & Integer.MAX_VALUE) % mod;
	}

}
