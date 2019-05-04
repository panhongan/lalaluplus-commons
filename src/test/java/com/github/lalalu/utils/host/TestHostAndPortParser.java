package com.github.lalalu.utils.host;

/**
 * lalalu plus
 */
public class TestHostAndPortParser {
	
	public static void main(String [] args) throws Exception {
		String uri = "192.168.0.10:2181,192.168.0.11:2181,192.168.0.12:2181";
		System.out.println(HostAndPortParser.parse(uri));
		
		String uri1 = "192.168.0.10:2181,192.168.0.11:2181,192.168.0.13:abc";
		System.out.println(HostAndPortParser.parse(uri1));
	}

}
