package com.github.panhongan.util.process;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pha.java.util.collection.CollectionUtil;

public class RuntimeUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(RuntimeUtil.class);
	
	public static boolean exec(String cmd, List<String> output, List<String> err) {
		boolean ret = false;
		
		try {
			Runtime runtime = Runtime.getRuntime();
			Process process = runtime.exec(cmd);
	
			ret = readData(process, output, err);
		} catch (Exception e) {
			logger.warn(e.getMessage());
			err.add(e.getMessage());
		}
		
		return ret;
	}
	
	public static boolean exec(String [] cmd, List<String> output, List<String> err) {
		boolean ret = false;
		
		try {
			Runtime runtime = Runtime.getRuntime();
			Process process = runtime.exec(cmd);
			
			ret = readData(process, output, err);
		} catch (Exception e) {
			logger.warn(e.getMessage());
			err.add(e.getMessage());
		}
		
		return ret;
	}
	
	public static boolean exec(List<String> cmd, List<String> output, List<String> err) {
		boolean ret = false;
		if (!CollectionUtil.isEmpty(cmd)) {
			ret = exec(cmd.toArray(new String[0]), output, err);
		}
		return ret;
	}
	
	private static boolean readData(Process process, List<String> output, List<String> err) {
		boolean ret = false;
		
		if (output != null) {
			output.clear();
		}
		if (err != null) {
			err.clear();
		}
		
		try {
			ProcessDataReader outputReader = new ProcessDataReader(process.getInputStream(), output);
			outputReader.start();
		
			ProcessDataReader errReader = new ProcessDataReader(process.getErrorStream(), err);
			errReader.start();
		
			outputReader.waitFor();
			errReader.waitFor();
			
			ret = (process.waitFor() == 0);
		} catch (Exception e) {
			logger.warn(e.getMessage());
		}
		
		return ret;
	}
	
	public static void main(String [] args) {
		List<String> output = new ArrayList<String>();
		List<String> err = new ArrayList<String>();
		boolean ret = RuntimeUtil.exec(new String[]{"python", "1.py"}, output, err);
		if (ret) {
			System.out.println(output.toString());
		} else {
			System.out.println(err.toString());
		}
	}

}
