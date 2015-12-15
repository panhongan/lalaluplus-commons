package pha.java.util.conf;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import pha.java.util.StringUtil;


public class Config {
	
	private static Logger logger = Logger.getLogger(Config.class);
	
	private Map<String, String> key_values = new HashMap<String, String>();
	
	public boolean parse(String confFile) {
		boolean ret = false;
		
		BufferedReader br = null;
		
		try {
			br = new BufferedReader(new FileReader(confFile));

			String line = null;
			while ((line = br.readLine()) != null) {
				line = line.trim();
				if (StringUtil.isEmpty(line) || line.startsWith("#")) {
					continue;
				}
				
				String [] arr = line.split("=");
				if (arr != null && arr.length == 2) {
					String key = arr[0].trim();
					String value = arr[1].trim();
					key_values.put(key, value);
				}
			}
			
			ret = true;
		} catch (Exception e) {
			logger.warn(e.getMessage());
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (Exception e) {
					logger.warn(e.getMessage());
				}
			}
		}
		
		return ret;
	}
	
	public String get(String key) {
		return key_values.get(key);
	}
	
	public String get(String key, String defaultVal) {
		String ret = key_values.get(key);
		if (ret == null) {
			ret = defaultVal;
		}
		return ret;
	}
	
	public int get(String key, int defaultVal) {
		int ret = defaultVal;
		
		try {
			ret = Integer.parseInt(key_values.get(key));
		} catch (Exception e) {
			
		}
		
		return ret;
	}
	
	public float get(String key, float defaultVal) {
		float ret = defaultVal;
		
		try {
			ret = Float.parseFloat(key_values.get(key));
		} catch (Exception e) {
			
		}
		
		return ret;
	}
	
	public double get(String key, double defaultVal) {
		double ret = defaultVal;
		
		try {
			ret = Double.parseDouble(key_values.get(key));
		} catch (Exception e) {
			
		}
		
		return ret;
	}
	
	public boolean get(String key, boolean defaultVal) {
		boolean ret = defaultVal;
		
		try {
			ret = Boolean.parseBoolean(key_values.get(key));
		} catch (Exception e) {
			
		}
		
		return ret;
	}
	
	public int propertyCount() {
		return key_values.size();
	}
	
	@Override
	public String toString() {
		int i = 0;
		StringBuffer sb = new StringBuffer();
		sb.append("Map: [");
		for (String key : key_values.keySet()) {
			sb.append(key);
			sb.append(" = ");
			sb.append(key_values.get(key));
			
			if (++i < key_values.size()) {
				sb.append(", ");
			}
		}
		sb.append("]");
		
		return sb.toString();
	}

}
