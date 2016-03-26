package com.github.panhongan.util.mq.kafka;

import com.github.panhongan.util.control.Lifecycleable;

public abstract class AbstractMessageProcessor implements Lifecycleable {
	
	private String name = null;
	
	public AbstractMessageProcessor() {
	}
	
	public AbstractMessageProcessor(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public abstract Object processMessage(String topic, int partition_id, String message);
	
}
