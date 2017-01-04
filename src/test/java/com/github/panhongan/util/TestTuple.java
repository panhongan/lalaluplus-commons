package com.github.panhongan.util;

public class TestTuple {
	
	public static void main(String [] args) {
		Tuple.Tuple2<String, String> t2 = new Tuple.Tuple2<String, String>("111", "222");
		System.out.println(t2);
		
		Tuple.Tuple3<String, String, Boolean> t3 = new Tuple.Tuple3<String, String, Boolean>("111", "222", true);
		System.out.println(t3);
		
		Tuple.Tuple4<String, String, Boolean, Integer> t4 = 
				new Tuple.Tuple4<String, String, Boolean, Integer>("111", "222", true, 33);
		System.out.println(t4);
		
		Tuple.Tuple5<String, String, Boolean, Integer, String> t5 = 
				new Tuple.Tuple5<String, String, Boolean, Integer, String>("111", "222", true, 33, "44");
		System.out.println(t5);
		System.out.println(t5._1);
		System.out.println(t5._2);
		System.out.println(t5._3);
		System.out.println(t5._4);
		System.out.println(t5._5);
	}

}
