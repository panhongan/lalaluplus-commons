package com.github.panhongan.util;

import com.github.panhongan.util.StringUtil;

public class TestStringUtil {
	
	public static void main(String [] args) {
		String str = "abc def gh";
		System.out.println(StringUtil.trimPrefix(str, "abc"));
		System.out.println(StringUtil.trimSuffix(str, "h"));
		
		System.out.println(StringUtil.trimSuffix(StringUtil.trimPrefix(str, "ab"), "h"));
		System.out.println(StringUtil.trim(str, "ab", "h"));
	}

}
