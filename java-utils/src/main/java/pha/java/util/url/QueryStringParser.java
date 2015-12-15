package pha.java.util.url;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

public class QueryStringParser {
	
	private static Logger logger = Logger.getLogger(QueryStringParser.class);
	
	public static Map<String, String> parse(String queryString) {
		Map<String, String> paramMap = new HashMap<String, String>();
		StringTokenizer st = new StringTokenizer(queryString, "&");
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
