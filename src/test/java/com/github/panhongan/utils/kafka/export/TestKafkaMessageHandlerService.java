package com.github.panhongan.utils.kafka.export;

import com.github.panhongan.utils.kafka.handler.AbstractMessageHandler;
import com.github.panhongan.utils.kafka.handler.KafkaMessageService;
import com.github.panhongan.utils.kafka.handler.KafkaMessageServiceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * lalalu plus
 */
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
		KafkaMessageServiceConfig config = new KafkaMessageServiceConfig();
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
		KafkaMessageService service = new KafkaMessageService();
		service.setMessageHandler(converter);
		service.setConfig(config);
		if (service.init()) {
			logger.info("{} init ok", CLASS_NAME);

			Runtime.getRuntime().addShutdownHook(new Thread(() -> service.uninit()));
		} else {
			logger.warn("{} init failed", CLASS_NAME);
			service.uninit();
		}
	}


}
