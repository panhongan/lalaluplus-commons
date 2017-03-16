package com.github.panhongan.util.kafka.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KafkaMessageServiceShutdownHook extends Thread {
	
	private static final Logger logger = LoggerFactory.getLogger(KafkaMessageServiceShutdownHook.class);
	
	private static final String CLASS_NAME = KafkaMessageServiceShutdownHook.class.getSimpleName();
	
	private KafkaMessageService service = null;
	
	public KafkaMessageServiceShutdownHook(KafkaMessageService service) {
		this.service = service;
	}

	@Override
	public void run() {
		if (service != null) {
			service.uninit();
		}
		
		logger.info("{} is trigged", CLASS_NAME);
	}
	
}
