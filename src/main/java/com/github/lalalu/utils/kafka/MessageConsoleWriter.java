package com.github.lalalu.utils.kafka;

import java.util.List;

/**
 * lalalu plus
 */
public class MessageConsoleWriter extends AbstractKafkaMessageProcessor {

	@Override
	public Object processMessage(String topic, int partition_id, String message) {
		System.out.println(topic + "_" + partition_id + " : " + message);
		return true;
	}
	
	@Override
	public Object processMessage(String topic, int partition_id, List<String> msg_list) {
		for (String message : msg_list) {
			System.out.println(topic + "_" + partition_id + " : " + message);
		}
		return true;
	}


	@Override
	public boolean init() {
		return true;
	}

	@Override
	public void uninit() {
	}

}
