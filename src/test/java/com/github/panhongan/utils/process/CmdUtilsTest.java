package com.github.panhongan.utils.process;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lalalu plus
 * @since 2017.6.7
 */

public class CmdUtilsTest {

	@Test
	public void testExecShellCmd() {
		List<String> output = new ArrayList<>();
		List<String> err = new ArrayList<>();
		boolean ret = CmdUtils.execShellCmd("cd ./; ls -l ./", output, err);
		assert (ret);
	}

	@Test
	public void testExecNonShellCmd() {
		List<String> output = new ArrayList<>();
		List<String> err = new ArrayList<>();
		boolean ret = CmdUtils.execNonShellCmd("python src/test/python/1.py", output, err);
		assert (ret);
	}
}
