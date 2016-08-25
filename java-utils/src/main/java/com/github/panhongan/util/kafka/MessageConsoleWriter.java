package com.github.panhongan.util.kafka;


import java.util.List;

import com.github.panhongan.util.kafka.AbstractMessageProcessor;


public class MessageConsoleWriter extends AbstractMessageProcessor {

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
