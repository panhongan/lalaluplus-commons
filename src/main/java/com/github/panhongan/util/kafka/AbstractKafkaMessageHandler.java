package com.github.panhongan.util.kafka;

import java.util.List;

import com.github.panhongan.util.control.Lifecycleable;

public abstract class AbstractKafkaMessageHandler implements Lifecycleable {
	
	private String name = null;
	
	public AbstractKafkaMessageHandler() {
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public AbstractKafkaMessageHandler(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	/**
	 * @param topic : data source topic
	 * @param partition_id : data source topic partition
	 * @param message : message
	 * @return Object
	 */
	public abstract Object processMessage(String topic, int partition_id, String message);
	
	/**
	 * @param topic : data source topic
	 * @param partition_id : data source topic partition
	 * @param message : message set
	 * @return Object
	 */
	public abstract Object processMessage(String topic, int partition_id, List<String> message);
	
}
