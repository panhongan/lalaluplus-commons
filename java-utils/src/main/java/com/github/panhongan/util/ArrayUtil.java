package com.github.panhongan.util;

public class ArrayUtil {
	
	public static String [] expand(String [] arr, int expand_len, String placeholder) {
		if (arr.length >= expand_len) {
			return arr;
		}
		
		String [] ret = new String[expand_len];
		for (int i = 0; i < arr.length; ++i) {
			ret[i] = arr[i];
		}
		
		for (int i = arr.length; i < expand_len; ++i) {
			ret[i] = placeholder;
		}
		
		return ret;
	}

}
