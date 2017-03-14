package com.github.panhongan.util.kafka.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KafkaMessageHandlerServiceShutdownHook extends Thread {
	
	private static final Logger logger = LoggerFactory.getLogger(KafkaMessageHandlerServiceShutdownHook.class);
	
	private static final String CLASS_NAME = KafkaMessageHandlerServiceShutdownHook.class.getSimpleName();
	
	private KafkaMessageHandlerService service = null;
	
	public KafkaMessageHandlerServiceShutdownHook(KafkaMessageHandlerService service) {
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
