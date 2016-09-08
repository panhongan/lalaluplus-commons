package com.github.panhongan.util.kafka;

import java.util.Map;

public class TestKafkaUtil {

	public static void main(String[] args) {
		Map<Integer, Long> map = KafkaUtil.getLastestWriteOffset("test1:9096", "test");
		for (Integer key : map.keySet()) {
			System.out.println(key + " , " + map.get(key));
		}
	}

}
