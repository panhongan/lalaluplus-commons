package com.github.panhongan.util;

import java.util.ArrayList;
import java.util.List;

public class StringUtil {

	public static List<String> sperate(String str, String regex) {
		List<String> list = new ArrayList<String>();
		
		String [] arr = str.split(regex);
		if (arr != null && arr.length > 0) {
			for (int i = 0; i < arr.length; ++i) {
				if (!arr[i].isEmpty()) {
					list.add(arr[i]);
				}
			}
		}
		
		return list;
	}
	
	public static String toString(Object obj) {
		String str = "null";
		if (obj != null) {
			str = obj.toString();
		}
		return str;
	}
	
	public static String trimPrefix(String str, String prefix) {
		String ret = str;
		if (str != null && str.startsWith(prefix)) {
			ret = str.substring(prefix.length());
		}
		return ret;
	}
	
	public static String trimSuffix(String str, String suffix) {
		String ret = str;
		if (str != null && str.endsWith(suffix)) {
			ret = str.substring(0, str.length() - suffix.length());
		}
		return ret;
	}
	
	public static String trim(String str, String prefix, String suffix) {
		String ret = str;
		if (str != null) {
			int start_offset = 0;
			int end_offset = str.length();
			if (prefix != null && str.startsWith(prefix)) {
				start_offset += prefix.length();
			}
			if (suffix != null && str.endsWith(suffix)) {
				end_offset -= suffix.length();
			}
			
			ret = str.substring(start_offset, end_offset);
		}
		return ret;
	}

}
