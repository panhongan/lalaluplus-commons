package com.github.panhongan.util.conf;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.panhongan.util.StringUtil;


public class Config implements Serializable {

	private static final long serialVersionUID = 3641484340330072531L;

	private static Logger logger = LoggerFactory.getLogger(Config.class);
	
	private Map<String, String> key_values = new HashMap<String, String>();
	
	private boolean parseExec(String conf_file) {
		boolean ret = false;
		BufferedReader br = null;
		
		try {
			br = new BufferedReader(new FileReader(conf_file));

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
	
	public boolean parse(String conf_file) {
		key_values.clear();
		return this.parseExec(conf_file);
	}
	
	public boolean parseResourceFile(String resource_file) {
		key_values.clear();
		boolean ret = false;
		
	    Properties prop = new Properties();

	    try {
	      prop.load(this.getClass().getResourceAsStream(resource_file));
	      for (Object key : prop.keySet()) {
	    	  String str_key = (String)key;
	    	  this.addProperty(str_key, (String)prop.getProperty(str_key));
	      }
	      
	      ret = true;
	    } catch (Exception e) {
	      logger.warn(e.getMessage(), e);
	    }
	    
	    return ret;
	}
	
	public boolean addConf(String conf_file) {
		return this.parseExec(conf_file);
	}
	
	public void addProperty(String key, String value) {
		if (key != null && value != null) {
			key_values.put(key, value);
		}
	}
	
	public String getString(String key) {
		return key_values.get(key);
	}
	
	public String getString(String key, String defaultVal) {
		String ret = key_values.get(key);
		if (ret == null) {
			ret = defaultVal;
		}
		return ret;
	}
	
	public short getShort(String key) {
		return Short.parseShort(key_values.get(key));
	}
	
	public short getShort(String key, short defaultVal) {
		short ret = defaultVal;
		
		try {
			ret = Short.parseShort(key_values.get(key));
		} catch (Exception e) {
		}
		
		return ret;
	}
	
	public int getInt(String key) {
		return Integer.parseInt(key_values.get(key));
	}
	
	public int getInt(String key, int defaultVal) {
		int ret = defaultVal;
		
		try {
			ret = Integer.parseInt(key_values.get(key));
		} catch (Exception e) {
		}
		
		return ret;
	}
	
	public long getLong(String key) {
		return Long.parseLong(key_values.get(key));
	}
	
	public long getLong(String key, long defaultVal) {
		long ret = defaultVal;
		
		try {
			ret = Long.parseLong(key_values.get(key));
		} catch (Exception e) {
		}
		
		return ret;
	}
	
	public float getFloat(String key) {
		return Float.parseFloat(key_values.get(key));
	}
	
	public float getFloat(String key, float defaultVal) {
		float ret = defaultVal;
		
		try {
			ret = Float.parseFloat(key_values.get(key));
		} catch (Exception e) {
		}
		
		return ret;
	}
	
	public double getDouble(String key) {
		return Double.parseDouble(key_values.get(key));
	}
	
	public double getDouble(String key, double defaultVal) {
		double ret = defaultVal;
		
		try {
			ret = Double.parseDouble(key_values.get(key));
		} catch (Exception e) {
		}
		
		return ret;
	}
	
	public boolean getBoolean(String key) {
		return Boolean.parseBoolean(key_values.get(key));
	}
	
	public boolean getBoolean(String key, boolean defaultVal) {
		boolean ret = defaultVal;
		
		try {
			ret = Boolean.parseBoolean(key_values.get(key));
		} catch (Exception e) {	
		}
		
		return ret;
	}
	
	public int size() {
		return key_values.size();
	}
	
	public boolean isEmpty() {
		return key_values.isEmpty();
	}
	
	public Set<String> keySet() {
		return key_values.keySet();
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
