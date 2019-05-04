package com.github.lalalu.utils.fs;

import java.util.List;

/**
 * lalalu plus
 */

public class TestFileUtils {

	public static void main(String[] args) {
		List<String> list = FileUtils.readFile(args[0]);
		if (list != null) {
			System.out.println(list);
		}

		FileUtils.readFile(args[0], x -> System.out.println(x));
	}

}
