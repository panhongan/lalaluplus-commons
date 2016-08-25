package com.github.panhongan.util.kafka;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TestKafkaUtil {

	public static void main(String[] args) {
		List<String> list = new ArrayList<String>();
		list.add("test1");
		Map<Integer, Long> map = KafkaUtil.getLastOffset(list, 9092, "test");
		for (Integer key : map.keySet()) {
			System.out.println(key + " , " + map.get(key));
		}
	}

}
