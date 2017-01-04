package com.github.panhongan.util;

public class TestOptional {

	public static void main(String[] args) {
		Optional<String> opt = new Optional<String>("abc");
		System.out.println(opt.isEmpty());
		System.out.println(opt.get());
		System.out.println(opt.getOrElse("11"));
		
		opt = new Optional<String>(null);
		System.out.println(opt.isEmpty());
		System.out.println(opt.get());
		System.out.println(opt.getOrElse("11"));
	}

}
