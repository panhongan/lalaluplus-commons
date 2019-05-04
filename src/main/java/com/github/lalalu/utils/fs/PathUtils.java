package com.github.lalalu.utils.fs;

import java.io.File;

import org.apache.commons.lang3.StringUtils;

/**
 * lalalu plus
 */

public class PathUtils {

	public static String absolutePath(String path) {
		if (StringUtils.isNotEmpty(path)) {
			File file = new File(path);
			return file.getAbsolutePath();
		} else {
			return null;
		}
	}

	public static void createRecursiveDir(String dir) {
		try {
			File file = new File(dir);
			if (!file.exists()) {
				file.mkdirs();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
