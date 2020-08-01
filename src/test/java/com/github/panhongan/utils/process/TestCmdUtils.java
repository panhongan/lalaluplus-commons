package com.github.panhongan.utils.process;

import java.util.ArrayList;
import java.util.List;

/**
 * lalalu plus
 */

public class TestCmdUtils {

	public static void main(String[] args) {
		List<String> output = new ArrayList<>();
		List<String> err = new ArrayList<>();
		boolean ret = CmdUtils.exec(new String[]{"python", "1.py"}, output, err);
		if (ret) {
			System.out.println(output.toString());
		} else {
			System.out.println(err.toString());
		}
	}

}
