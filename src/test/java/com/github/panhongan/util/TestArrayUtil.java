package com.github.panhongan.util;

import com.github.panhongan.util.collection.ArrayUtil;

public class TestArrayUtil {
	
	public static void main(String [] args) {
		String str = "abc&d&e";
		String [] arr = str.split("&");
		
		for (int i = 0; i < arr.length; ++i) {
			System.out.println(arr[i]);
		}
		
		String [] arr1 = ArrayUtil.expand(arr, 4, "");
		for (int i = 0; i < arr1.length; ++i) {
			System.out.println(arr1[i]);
		}
	}

}
