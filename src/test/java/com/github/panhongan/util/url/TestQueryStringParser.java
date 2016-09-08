package com.github.panhongan.util.url;

import java.util.Map;

import com.github.panhongan.util.web.url.QueryStringParser;

public class TestQueryStringParser {
	
	public static void main(String [] args) {
		String url = "http://www.baidu.com/a?a=b&c=d";
		Map<String, String> params = QueryStringParser.parse(url);
		System.out.println(params.toString());
		System.out.println(params.get("e"));
		System.out.println(params.get("a"));
		
		url = "http://mp.weixin.qq.com/s?__biz=MzA3ODAyODI0Mg==&mid=402113020&idx=6&sn=5d741f399eb72be2dbd2dce32890e870&scene=4#wechat_redirect";
		params = QueryStringParser.parse(url);
		System.out.println(params);
		System.out.println(params.get("__biz"));
	}

}
