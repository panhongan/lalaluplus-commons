package com.github.lalalu.utils.kafka;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.RecordMetadata;

/**
 * lalalu plus
 */
public class KafkaProducerSendFailedCallback implements Callback {

	private MessageLocalWriter local_writer = null;
	
	private String topic = null;
	
	private String message = null;
	
	private int partition_id = 0;
	
	public KafkaProducerSendFailedCallback(MessageLocalWriter local_writer, 
			String topic, int partition_id, String message) {
		this.local_writer = local_writer;
		this.topic = topic;
		this.message = message;
		this.partition_id = partition_id;
	}

	@Override
	public void onCompletion(RecordMetadata metadata, Exception e) {
		if (e != null) {
			if (local_writer != null) {
				local_writer.processMessage(topic, partition_id, message);
			}
		}
	}

}
