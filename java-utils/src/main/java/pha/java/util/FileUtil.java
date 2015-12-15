package pha.java.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class FileUtil {
	
	private static Logger logger = Logger.getLogger(FileUtil.class);
	
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

}
