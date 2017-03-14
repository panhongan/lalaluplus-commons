package com.github.panhongan.util.kafka.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.panhongan.util.kafka.AbstractKafkaMessageHandler;
import com.github.panhongan.util.kafka.MessageKafkaWriter;
import com.github.panhongan.util.thread.ControllableThread;

public class KafkaMessageWorkflow extends AbstractKafkaMessageHandler {

	private static final Logger logger = LoggerFactory.getLogger(KafkaMessageWorkflow.class);

	private static final int QUEUE_CAPACITY = 200 * 1000;

	private List<InnerMessageProcessor> inner_processors = new ArrayList<InnerMessageProcessor>();

	private List<ArrayBlockingQueue<Message>> queues = new ArrayList<ArrayBlockingQueue<Message>>();

	private AbstractMessageHandler handler = null;
	
	private MessageKafkaWriter kafka_writer = null;
	
	private KafkaMessageHandlerServiceConfig config = null;

	private Random random = new Random();

	private int queue_index = 0;

	private int inner_queue_num = 2;

	private int inner_processors_per_queue = 2;

	public KafkaMessageWorkflow(AbstractMessageHandler handler, MessageKafkaWriter kafka_writer, KafkaMessageHandlerServiceConfig config) {
		this.handler = handler;
		this.kafka_writer = kafka_writer;
		this.config = config;

		try {
			inner_queue_num = config.getConfig().getInt("inner.queue.num");
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
		}

		try {
			inner_processors_per_queue = config.getConfig().getInt("inner.processors.per.queue");
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
		}
	}

	@Override
	public boolean init() {
		boolean is_ok = (handler != null && kafka_writer != null);
		if (!is_ok) {
			logger.warn("Converter is null or kafka writer is null");
			return is_ok;
		}

		for (int i = 0; i < inner_queue_num; ++i) {
			// inner queue
			ArrayBlockingQueue<Message> queue = new ArrayBlockingQueue<Message>(QUEUE_CAPACITY);
			queues.add(queue);

			// InnerMessageProcessor
			for (int j = 0; j < inner_processors_per_queue; ++j) {
				AbstractMessageHandler converter_clone = (AbstractMessageHandler) handler.clone();
				if (converter_clone == null || !converter_clone.init()) {
					logger.warn("converter clone failed");
					break;
				}

				int instance_id = i * inner_processors_per_queue + j;
				InnerMessageProcessor processor = new InnerMessageProcessor(converter_clone, queue, kafka_writer);
				processor.setName("InnerMessageProcessor_" + instance_id);
				processor.setSleepInterval(0);
				if (processor.init()) {
					inner_processors.add(processor);
				} else {
					logger.warn("{} init failed", processor.getName());
					break;
				}
			}
		}

		is_ok = (inner_processors.size() == inner_queue_num * inner_processors_per_queue);
		return is_ok;
	}

	@Override
	public void uninit() {
		// wait for all data to be processed
		logger.info("wait queue data to be processed completely...");

		while (true) {
			boolean can_quit = true;

			for (ArrayBlockingQueue<Message> queue : queues) {
				if (!queue.isEmpty()) {
					can_quit = false;
				}
			}

			if (can_quit) {
				break;
			} else {
				try {
					Thread.sleep(1 * 1000);
				} catch (Exception e) {
				}
			}
		}

		for (InnerMessageProcessor processor : inner_processors) {
			processor.uninit();
		}
		logger.info("all InnerMessageProcessors unit");
	}

	@Override
	public Object processMessage(String topic, int partition_id, String message) {
		try {
			ArrayBlockingQueue<Message> queue = queues.get(queue_index);
			queue.put(new Message(topic, partition_id, message));
			
			// 1/20 to check the queue
			if (random.nextInt(100) <= 5) {
				logger.info("{}_{}_queue_{} :  queue_size = {}", topic, partition_id, queue_index, queue.size()); 
			}
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
		}

		queue_index = (++queue_index % inner_queue_num);

		return true;
	}

	@Override
	public Object processMessage(String topic, int partition_id, List<String> msg_list) {
		for (String message : msg_list) {
			this.processMessage(topic, partition_id, message);
		}

		return true;
	}

	private class Message {
		
		public String topic = null;

		public int partition_id = -1;

		public String message = null;

		public Message(String topic, int partition_id, String message) {
			this.topic = topic;
			this.partition_id = partition_id;
			this.message = message;
		}
	}

	public class InnerMessageProcessor extends ControllableThread {
		
		private AbstractMessageHandler converter = null;

		private MessageKafkaWriter kafka_writer = null;

		private ArrayBlockingQueue<Message> queue = null;
		
		public InnerMessageProcessor(AbstractMessageHandler abstractConveter, ArrayBlockingQueue<Message> queue,
				MessageKafkaWriter kafka_writer) {
			this.converter = abstractConveter;
			this.queue = queue;
			this.kafka_writer = kafka_writer;
		}

		@Override
		public boolean init() {
			boolean is_ok = (converter != null && queue != null && kafka_writer != null);
			if (!is_ok) {
				logger.warn("Converter or queue is null");
				return is_ok;
			}

			is_ok = super.init();

			return is_ok;
		}

		@Override
		public void uninit() {
			super.uninit();

			if (converter != null) {
				converter.uninit();
			}
		}

		@Override
		protected void work() {
			try {
				Message msg = queue.poll(1L, TimeUnit.MILLISECONDS);
				if (msg != null) {
					Map<String, List<String>> convert_results = converter.convert(msg.message);
					for (String dst_topic : convert_results.keySet()) {
						kafka_writer.setSendTopic(dst_topic);
						kafka_writer.processMessage(msg.topic, msg.partition_id, convert_results.get(dst_topic));
					}
				}
			} catch (Exception e) {
				logger.warn(e.getMessage(), e);
			}
		}
	} // end class InnerMessageProcessor

}
