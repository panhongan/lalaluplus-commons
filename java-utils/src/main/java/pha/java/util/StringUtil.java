package pha.java.util;

import java.util.ArrayList;
import java.util.List;

public class StringUtil {
	
	public static boolean isEmpty(String str){
		return (ObjectUtil.isNull(str) || str.isEmpty());
	}
	
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

}
