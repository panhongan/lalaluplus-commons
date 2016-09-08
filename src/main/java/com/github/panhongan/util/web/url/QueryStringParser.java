package com.github.panhongan.util.web.url;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class QueryStringParser {
	
	private static final Logger logger = LoggerFactory.getLogger(QueryStringParser.class);
	
	public static Map<String, String> parse(String url) {
		Map<String, String> paramMap = new HashMap<String, String>();
		int begin_pos = url.indexOf('?') + 1;
		int end_pos = url.indexOf('#', begin_pos);
		if (end_pos == -1) {
			end_pos = url.length();
		}
		
		StringTokenizer st = new StringTokenizer(url.substring(begin_pos, end_pos), "&");
        while (st.hasMoreTokens()) {
            String pairs = st.nextToken();
            int pos = pairs.indexOf('=');
            if (pos >= 0) {
            	String value = pairs.substring(pos + 1);
            	try {
            		value = URLDecoder.decode(value, "utf-8");
            	} catch (Exception e) {
            		logger.warn(e.getMessage());
            	}
            	
            	paramMap.put(pairs.substring(0, pos), value);
            }
        }
        
        return paramMap;
	}

}
