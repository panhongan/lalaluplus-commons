package com.github.panhongan.utils.fs;

import java.io.File;

import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;

/**
 * @author lalalu plus
 * @since 2017.7.10
 * @version 1.0
 */

public class PathUtils {

	public static String absolutePath(String path) {
		Preconditions.checkArgument(StringUtils.isNotEmpty(path));
		File file = new File(path);
		return file.getAbsolutePath();
	}

	public static void createRecursiveDir(String dir) {
	    File file = new File(dir);
	    if (!file.exists()) {
	        file.mkdirs();
	    }
	}
}
