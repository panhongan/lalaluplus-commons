package com.github.lalalu.utils.kafka;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.Producer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.lalalu.utils.conf.Config;
import com.github.lalalu.utils.control.Lifecycleable;

/**
 * lalalu plus
 */
public class MessageKafkaWriter extends AbstractKafkaMessageProcessor implements Lifecycleable {

	private static final Logger logger = LoggerFactory.getLogger(MessageKafkaWriter.class);

	private Config producer_config = null;

	private Producer<String, String> producer = null;

	private MessageLocalWriter local_writer = null;

	private String send_topic = null;

	private boolean sync = false;

	/**
	 * @param producer_config      : producer config
	 * @param send_failed_data_dir : if send failed, records will saved here
	 */
	public MessageKafkaWriter(Config producer_config, String send_failed_data_dir) {
		this(producer_config, null, send_failed_data_dir);
	}

	/**
	 * @param producer_config      : producer config
	 * @param dst_topic            : send topic
	 * @param send_failed_data_dir : if send failed, records will saved here
	 */
	public MessageKafkaWriter(Config producer_config, String dst_topic, String send_failed_data_dir) {
		this.producer_config = producer_config;
		this.send_topic = dst_topic;
		this.sync = producer_config.getString("producer.type", "async").contentEquals("sync");

		if (StringUtils.isNotEmpty(send_failed_data_dir)) {
			local_writer = new MessageLocalWriter(send_failed_data_dir, 1);
		}
	}

	public void setSendTopic(String send_topic) {
		this.send_topic = send_topic;
	}

	@Override
	public boolean init() {
		boolean is_ok = false;

		is_ok = KafkaUtils.isKafkaClusterAlive(producer_config.getString("zookeeper.connect"));
		if (!is_ok) {
			logger.warn("Kafka cluster is not alive");
			return is_ok;
		}

		logger.info("Kafka cluster is alive");

		try {
			producer = KafkaUtils.createProducer(producer_config);
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
		}

		return (producer != null);
	}

	@Override
	public void uninit() {
		KafkaUtils.closeProducer(producer);

		if (local_writer != null) {
			local_writer.uninit();
		}
	}

	@Override
	public Object processMessage(String topic, int partition_id, String message) {
		if (sync) {
			if (!KafkaUtils.sendSync(producer, send_topic, message)) {
				logger.warn("send message to kafka failed : {}", message);

				// 写入本地文件
				if (local_writer != null) {
					local_writer.processMessage(send_topic, partition_id, message);
				}
			}
		} else {
			Callback callback = null;
			if (local_writer != null) {
				callback = new KafkaProducerSendFailedCallback(local_writer, send_topic, partition_id, message);
			}
			KafkaUtils.sendAsync(producer, send_topic, message, callback);
		}

		return true;
	}

	@Override
	public Object processMessage(String topic, int partition_id, List<String> msg_list) {
		for (String message : msg_list) {
			this.processMessage(topic, partition_id, message);
		}

		return true;
	}

}
