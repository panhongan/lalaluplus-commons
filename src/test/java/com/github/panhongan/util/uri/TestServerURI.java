package com.github.panhongan.util.uri;

public class TestServerURI {
	
	public static void main(String [] args) {
		String uri = "192.168.0.10:2181,192.168.0.11:2181,192.168.0.12:2181";
		System.out.println(ServerURI.parse(uri));
		
		String uri1 = "192.168.0.10:2181,192.168.0.11:2181,192.168.0.13:abc";
		System.out.println(ServerURI.parse(uri1));
	}

}
