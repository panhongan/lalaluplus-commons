package com.github.panhongan.util.path;

/**
 * lalalu plus
 */

public class TestPathUtils {
	
	public static void main(String [] args) {
		String abs_path = PathUtils.absolutePath("");
		if (abs_path != null) {
			System.out.println(abs_path);
		}
		
		abs_path = PathUtils.absolutePath("/");
		if (abs_path != null) {
			System.out.println(abs_path);
		}
	}

}