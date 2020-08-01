package com.github.panhongan.utils.kafka;

import java.util.Map;

/**
 * lalalu plus
 */
public class TestKafkaUtil {

	public static void main(String[] args) {
		Map<Integer, Long> map = KafkaUtils.getLastestWriteOffset("test1:9096", "test");
		for (Integer key : map.keySet()) {
			System.out.println(key + " , " + map.get(key));
		}
	}

}
