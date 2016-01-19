package com.github.panhongan.util.collection;

import java.util.Collection;

import com.github.panhongan.util.ObjectUtil;

public class CollectionUtil {
	
	public static boolean isEmpty(Collection<?> co) {
		return (co == null || co.isEmpty());
	}

	public static String formatCollection(Collection<?> co, String seperator) {
		StringBuffer sb = new StringBuffer();
		
		if (co.size() > 0) {
			Object [] objArr = co.toArray();
			if (!ObjectUtil.isNull(objArr)) {
				for (int i = 0; i < objArr.length - 1; ++i) {
					sb.append(objArr[i].toString());
					sb.append(seperator);
				}
			
				sb.append(objArr[objArr.length - 1].toString());
			}
		}
		
		return sb.toString();
	}
	
}
