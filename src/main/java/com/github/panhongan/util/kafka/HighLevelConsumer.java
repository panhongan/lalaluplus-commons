package com.github.panhongan.util.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.message.MessageAndMetadata;

public class HighLevelConsumer implements Runnable {

	private static final Logger logger = LoggerFactory.getLogger(HighLevelConsumer.class);
	
	private AbstractKafkaMessageHandler msg_processor = null;

	private KafkaStream<byte[], byte[]> stream = null;

	private String topic = null;
	
	private boolean is_finished = false;

	public HighLevelConsumer(KafkaStream<byte[], byte[]> stream, String topic,
			AbstractKafkaMessageHandler msg_processor) {
		this.stream = stream;
		this.topic = topic;
		this.msg_processor = msg_processor;
	}
	
	@Override
	public void run() {
		try {
			ConsumerIterator<byte[], byte[]> it = stream.iterator();

			while (!is_finished && it.hasNext()) {
				MessageAndMetadata<byte[], byte[]> msg_metadata = it.next();
				msg_processor.processMessage(msg_metadata.topic(), msg_metadata.partition(),
								new String(msg_metadata.message()));
			}
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
		}
		
		logger.info("HighLevelConsumer {} stopped.", topic);
	}
	
	public void stopRunning() {
		this.is_finished = true;
	}

}
