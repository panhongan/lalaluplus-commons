package com.github.panhongan.util.kafka.export;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KafkaExporterShutdownHook extends Thread {
	
	private static final Logger logger = LoggerFactory.getLogger(KafkaExporterShutdownHook.class);
	
	private static final String CLASS_NAME = KafkaExporterShutdownHook.class.getSimpleName();
	
	private KafkaExporter service = null;
	
	public KafkaExporterShutdownHook(KafkaExporter service) {
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
