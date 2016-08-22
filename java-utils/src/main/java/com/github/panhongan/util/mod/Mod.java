package com.github.panhongan.util.mod;

public class Mod {
	
	public static long mod(long numerator, long denominator) throws ModException {
		if (denominator < 0L) {
			throw new ModException("denominator < 0");
		}
		
		return (numerator % denominator + denominator) % denominator;
	}
	
	public static int mod(int numerator, int denominator) throws ModException {
		if (denominator < 0) {
			throw new ModException("denominator < 0");
		}
		
		return (numerator % denominator + denominator) % denominator;
	}

}
