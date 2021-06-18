package com.github.panhongan.utils.process;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lalalu plus
 * @since 2017.6.7
 */

public class CmdUtilsTest {

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
