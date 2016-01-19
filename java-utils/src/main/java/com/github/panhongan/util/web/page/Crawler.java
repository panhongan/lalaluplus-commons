package com.github.panhongan.util.web.page;

import java.util.HashMap;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.github.panhongan.util.control.workflow.WorkflowNode;
import com.github.panhongan.util.web.url.HTTPConstants;

public class Crawler extends WorkflowNode {
	
	private static final Logger logger = LoggerFactory.getLogger(Crawler.class);
	
	private Map<String, String> headers = new HashMap<String, String>();
	
	public Crawler() {
		
	}
	
	public Crawler(String name) {
		super(name);
	}
	
	public void addHeader(String key, String value) {
		if (key != null && value != null) {
			headers.put(key, value);
		}
	}
	
	public void addHeaders(Map<String, String> map) {
		if (map != null) {
			for (String key : map.keySet()) {
				this.addHeader(key,  map.get(key));
			}
		}
	}
	
	public void clearHeaders() {
		headers.clear();
	}
	
	public void setHeader(String key, String value) {
		if (value != null && headers.containsKey(key)) {
			headers.put(key, value);
		}
	}
	
	public void removeHeader(String key) {
		if (key != null) {
			headers.remove(key);
		}
	}
	
	public Map<String, String> getHeaders() {
		return headers;
	}
	
	@Override
	public Object processData(Object input) {
		Document doc = null;
		
		if (input instanceof JSONObject) {
			try {
				JSONObject json = (JSONObject)input;
				String url = json.getString("url");
				String method = json.getString("method");
				doc = this.crawl(method, url);
			} catch (Exception e) {
				logger.warn(e.getMessage());
			}
		}
		
		return doc;
	}
	
	public Document crawl(String method, String url) {
		Document doc = null;
		
		if (url == null) {
			logger.warn("url is null");
			return doc;
		}
		
		try {
			Connection conn = Jsoup.connect(url);
			for (String key : headers.keySet()) {
				conn.header(key, headers.get(key));
			}
			conn.ignoreContentType(true);
			conn.timeout(5 * 1000);
			
			if (HTTPConstants.Method.GET.equals(method)) {
				doc = conn.get();
			} else if (HTTPConstants.Method.POST.equals(method)) {
				doc = conn.post();
			}
		} catch (Exception e) {
			logger.warn(e.getMessage());
		}
		
		return doc;
	}
	
}
