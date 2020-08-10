package com.github.panhongan.utils.fs;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.Test;

import java.util.List;

/**
 * @author lalalu plus
 * @since 2017.7.10
 * @version 1.0
 */

public class FileUtilsTest {

    private static final String TEST_FLE = ".gitignore";

    @Test
	public void testReadFile_ReturnData() {
        List<String> list = FileUtils.readFile(TEST_FLE);
        assert (CollectionUtils.isNotEmpty(list));
    }

    @Test
    public void testReadFile_Consumer() {
		FileUtils.readFile(TEST_FLE, x -> System.out.println(x));
	}
}
