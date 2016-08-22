package com.github.panhongan.util.hadoop;

import java.io.File;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;


public class MapReduceTaskXmlParser {

	public String parseXmlFile(String xmlFile) {
		StringBuffer sb = new StringBuffer();
		
		try {
			SAXReader reader = new SAXReader();
			Document document = reader.read(new File(xmlFile));
			Element element = document.getRootElement();
			
			for(Iterator i = element.elementIterator("property");
					i.hasNext();){
				//获取节点元素  
				element = (org.dom4j.Element)i.next();  
				String name = element.elementText("name");  
				String value = element.elementText("value");  

				sb.append(" -jobconf ");
				sb.append(name);
				sb.append("=");
				sb.append(value);
			}  
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return sb.toString();
	}
	
	public static void usage() {
		System.err.println("Usage : java MapReduceTaskXmlParser <task_conf_file>");
	}
	
	public static void main(String [] args) {
		if (args.length != 1) {
			System.err.println("Invalid parameter");
			usage();
			return;
		}
		
		String xmlFile = args[0];
		MapReduceTaskXmlParser parser = new MapReduceTaskXmlParser();
		String result = parser.parseXmlFile(xmlFile);
		System.out.println(result);
	}
}
