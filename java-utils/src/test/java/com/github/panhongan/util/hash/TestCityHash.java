package com.github.panhongan.util.hash;

public class TestCityHash {
	
	public static void main(String [] args) {
		String str = "abcd";
		System.out.println(CityHash.cityHash64(str.getBytes(), 0, str.length()));
	}

}
