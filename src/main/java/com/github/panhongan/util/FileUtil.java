package com.github.panhongan.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUtil {
	
	private static Logger logger = LoggerFactory.getLogger(FileUtil.class);
	
	public static List<String> readFile(String file) {
		List<String> list = new ArrayList<String>();
		
		BufferedReader br = null;
		
		try {
			br = new BufferedReader(new FileReader(file));
			String line = null;
			
			while ((line = br.readLine()) != null) {
				list.add(line);
			}
		} catch (Exception e) {
			logger.warn(e.getMessage());
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (Exception e) {
					
				}
			}
		}
		
		return list;
	}
	
	public static void readFile(String file, LineProcessor processor) {
		if (processor == null) {
			logger.warn("invalid parameter : processor");
			return;
		}
		
		BufferedReader br = null;
		
		try {
			br = new BufferedReader(new FileReader(file));
			String line = null;
			
			while ((line = br.readLine()) != null) {
				processor.process(line);
			}
		} catch (Exception e) {
			logger.warn(e.getMessage());
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (Exception e) {
					
				}
			}
		}
	}
	
	public static interface LineProcessor {
		public void process(String line);
	}

}
