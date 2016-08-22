package com.github.panhongan.util;

import java.util.List;

public class TestFileUtil {
	
	public static class TestLineProcessor implements FileUtil.LineProcessor {

		@Override
		public void process(String line) {
			System.out.println(line);
		}
		
	}
	
	public static void main(String [] args) {
		List<String> list = FileUtil.readFile(args[0]);
		if (list != null) {
			System.out.println(list);
		}
		
		FileUtil.readFile(args[0], new TestLineProcessor());
	}

}
