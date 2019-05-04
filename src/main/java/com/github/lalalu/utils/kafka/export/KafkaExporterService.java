package com.github.lalalu.utils.kafka.export;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * lalalu plus
 */
public class KafkaExporterService {
	
	private static final String CLASS_NAME = KafkaExporterService.class.getSimpleName();
	
	private static final Logger logger = LoggerFactory.getLogger(CLASS_NAME);
	
	public static void usage() {
		System.out.println(CLASS_NAME + " <conf_file>");
	}

	
	public static void main(String [] args) {
		if (args.length != 1) {
			usage();
			return;
		}
		
		// config
		String conf_file = args[0];
		KafkaExporterConfig config = KafkaExporterConfig.getInstance();
		if (!config.parse(conf_file)) {
			logger.warn("parse conf file failed : {}", conf_file);
			return;
		}
		
		logger.info(config.toString());
		
		if (!config.isValid()) {
			logger.warn("config is invalid");
			return;
		}

		// kafka exporter
		KafkaExporter exporter = new KafkaExporter();
		if (exporter.init()) {
			logger.info("{} init ok", CLASS_NAME);
			Runtime.getRuntime().addShutdownHook(new Thread(() -> exporter.uninit()));
		} else {
			logger.warn("{} init failed", CLASS_NAME);
			exporter.uninit();
		}
	}

}
