package com.github.panhongan.util.hadoop;

import org.apache.hadoop.mapred.lib.MultipleTextOutputFormat;
import org.apache.hadoop.io.Text;

/**
 * lalalu plus
 */

public class SuffixMultipleTextOutputFormat extends MultipleTextOutputFormat<Text, Text> {
	
	private int tagPos = -1;
	
	private boolean has_value = true;
	
	public void test(Text key, Text value, String name) {
		System.out.println("file_name = " + this.generateFileNameForKeyValue(key, value, name));
		System.out.println("key = " + this.generateActualKey(key, value));
		System.out.println("value = " + this.generateActualValue(key, value));
	}
	
	@Override
	protected Text generateActualKey(Text key, Text value) {
		if (!has_value && tagPos != -1) {
			return new Text(key.toString().substring(0, tagPos));
		}
		return key;
	}
	
	@Override
	protected Text generateActualValue(Text key, Text value) {
		if (has_value && tagPos != -1) {
			return new Text(value.toString().substring(0, tagPos));
		}
		return value;
	}
	
	@Override  
	protected String generateFileNameForKeyValue(Text key, Text value, String name) {
		has_value = true;
		tagPos = -1;
		
		String val = value.toString();
		if (val.isEmpty()) {
			has_value = false;
			val = key.toString();
		}
		
		try {
			int pos = val.lastIndexOf('#');
			if (pos >= 0 && pos == val.length() - 2) {
				char suffix = val.charAt(pos+1);
				if (Character.isDigit(suffix) || Character.isLetter(suffix)) {
					tagPos = pos;
					
					return name + "-" + suffix;
				} else {
					throw new RuntimeException("format exception : key = " + key.toString() + " , value = " + value.toString());
				}
			} else {
				throw new RuntimeException("format exception : key = " + key.toString() + " , value = " + value.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return name;
	}
	
	public static void main(String [] args) {
		new SuffixMultipleTextOutputFormat().test(new Text("2551180#A"), new Text(""), "part-00000");
		new SuffixMultipleTextOutputFormat().test(new Text("abc"), new Text("#"), "part-00001");
		new SuffixMultipleTextOutputFormat().test(new Text("abc"), new Text("w#o"), "part-00001");
		new SuffixMultipleTextOutputFormat().test(new Text("abc#0"), new Text(""), "part-00001");
		new SuffixMultipleTextOutputFormat().test(new Text("abc#0"), new Text("a"), "part-00001");
	}

}
