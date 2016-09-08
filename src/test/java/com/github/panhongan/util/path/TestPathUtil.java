package com.github.panhongan.util.path;

import java.util.List;

import com.github.panhongan.util.path.PathUtil;

public class TestPathUtil {
	
	public static void main(String [] args) {
		String abs_path = PathUtil.absolutePath("");
		if (abs_path != null) {
			System.out.println(abs_path);
		}
		
		abs_path = PathUtil.absolutePath("/");
		if (abs_path != null) {
			System.out.println(abs_path);
		}
		
		List<String> list = PathUtil.recursivePathList("/");
		if (list != null) {
			System.out.println(list.toString());
		}
		
		list = PathUtil.recursivePathList("");
		if (list != null) {
			System.out.println(list.toString());
		}
		
		list = PathUtil.recursivePathList("/a//");
		if (list != null) {
			System.out.println(list.toString());
		}
		
		list = PathUtil.recursivePathList("/a//b/c");
		if (list != null) {
			System.out.println(list.toString());
		}
	}

}
