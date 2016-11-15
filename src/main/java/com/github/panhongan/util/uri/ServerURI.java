package com.github.panhongan.util.uri;

import java.util.ArrayList;
import java.util.List;

import com.github.panhongan.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerURI {
	
	private static Logger logger = LoggerFactory.getLogger(ServerURI.class);
	
	// ip:port,ip:port,ip:port
	public static List< Pair<String, Integer> > parse(String uri) {
		List< Pair<String, Integer> > list = new ArrayList< Pair<String, Integer> >();
		
		try {
			String [] arr = uri.split("[:,]");
			if (arr.length % 2 == 0) {
				for (int i = 0; i < arr.length; i += 2) {
					list.add(new Pair<String, Integer>(arr[i], Integer.valueOf(arr[ i+1])));
				}
			} else {
				throw new Exception("Invalid URI : " + uri);
			}
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			list.clear();
		}
		
		return list;
	}

}
