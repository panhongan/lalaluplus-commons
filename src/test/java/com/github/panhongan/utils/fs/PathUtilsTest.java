package com.github.panhongan.utils.fs;

import org.junit.Test;

/**
 * @author lalalu plus
 * @since 2017.7.10
 * @version 1.0
 */

public class PathUtilsTest {

    @Test (expected = IllegalArgumentException.class)
    public void testAbsolutePath_Exception() {
        String path = PathUtils.absolutePath("");
        if (path != null) {
            System.out.println(path);
        }
    }

    @Test
	public void testAbsolutePath_CurrentDir() {
        String path = PathUtils.absolutePath(".");
        if (path != null) {
            System.out.println(path);
        }
    }

    @Test
    public void testAbsolutePath_RootDir() {
		String path = PathUtils.absolutePath("/");
		if (path != null) {
			System.out.println(path);
		}
	}
}
