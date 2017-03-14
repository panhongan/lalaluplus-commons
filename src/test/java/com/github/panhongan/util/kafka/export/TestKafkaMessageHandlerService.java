package com.github.panhongan.util.kafka.export;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.panhongan.util.kafka.handler.AbstractMessageHandler;
import com.github.panhongan.util.kafka.handler.KafkaMessageHandlerServiceConfig;
import com.github.panhongan.util.kafka.handler.KafkaMessageHandlerService;
import com.github.panhongan.util.kafka.handler.KafkaMessageHandlerServiceShutdownHook;


public class TestKafkaMessageHandlerService {
	
	private static Logger logger = LoggerFactory.getLogger(TestKafkaMessageHandlerService.class);
	
	private static final String CLASS_NAME = TestKafkaMessageHandlerService.class.getSimpleName();
	
	public static void usage() {
		System.out.println(CLASS_NAME + "<conf_file>");
	}

	public static void main(String[] args) {
		if (args.length != 1) {
			usage();
			return;
		}

		// config
		String conf_file = args[0];
		KafkaMessageHandlerServiceConfig config = new KafkaMessageHandlerServiceConfig();
		if (!config.parse(conf_file)) {
			logger.warn("parse conf file failed : {}", conf_file);
			return;
		}

		logger.info(config.toString());

		if (!config.isValid()) {
			logger.warn("config is invalid");
			return;
		}

		// converter
		AbstractMessageHandler converter = new DefaultMessageHandler();
		if (!converter.init()) {
			logger.warn("converter init failed");
			return;
		}
		
		// init service
		KafkaMessageHandlerService service = new KafkaMessageHandlerService();
		service.setMessageHandler(converter);
		service.setConfig(config);
		if (service.init()) {
			logger.info("{} init ok", CLASS_NAME);

			Runtime.getRuntime().addShutdownHook(new KafkaMessageHandlerServiceShutdownHook(service));
		} else {
			logger.warn("{} init failed", CLASS_NAME);
			service.uninit();
		}
	}


}
