package com.github.panhongan.util.mq.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.message.MessageAndMetadata;

public class HighLevelConsumer implements Runnable {

	private static final Logger logger = LoggerFactory.getLogger(HighLevelConsumer.class);
	
	private AbstractMessageProcessor msg_processor = null;

	private KafkaStream<byte[], byte[]> stream = null;

	private String topic = null;
	
	private int partition_id = -1;
	
	private boolean is_finished = false;

	public HighLevelConsumer(KafkaStream<byte[], byte[]> stream, String topic, int partition_id,
			AbstractMessageProcessor msg_processor) {
		this.stream = stream;
		this.topic = topic;
		this.partition_id = partition_id;
		this.msg_processor = msg_processor;
	}
	
	@Override
	public void run() {
		try {
			ConsumerIterator<byte[], byte[]> it = stream.iterator();

			while (!is_finished && it.hasNext()) {
				if (msg_processor != null) {
					MessageAndMetadata<byte[], byte[]> msg_metadata = it.next();
					msg_processor.processMessage(msg_metadata.topic(), 
							msg_metadata.partition(), new String(msg_metadata.message()));
				}
			}
			
			logger.info("HighLevelConsumer {}_{} stopped.", topic, partition_id);
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
		}
	}
	
	public void stopRunning() {
		this.is_finished = true;
	}

}
