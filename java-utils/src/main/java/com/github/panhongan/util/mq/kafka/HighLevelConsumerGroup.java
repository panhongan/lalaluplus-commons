package com.github.panhongan.util.mq.kafka;

import com.github.panhongan.util.control.Lifecycleable;
import com.github.panhongan.util.zookeeper.ZKUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kafka.consumer.Consumer;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;


public class HighLevelConsumerGroup implements Lifecycleable {
	
	private static final Logger logger = LoggerFactory.getLogger(HighLevelConsumerGroup.class);
	
	private List<AbstractMessageProcessor> msg_processors = null;
	
	private ConsumerConnector consumer = null;
	
	private ExecutorService executor = null;
	
	private String zk_list = null;
	
	private String group_id = null;
	
	private String topic = null;
	
	private int partition = 0;
	
	private boolean restart_offset_largest = true;
	
	public HighLevelConsumerGroup(String zk_list, String group_id,
			String topic, int partition, boolean restart_offset_largest,
			List<AbstractMessageProcessor> msg_processors) {
		this.zk_list = zk_list;
		this.group_id = group_id;
		this.topic = topic;
		this.partition = partition;
		this.restart_offset_largest = restart_offset_largest;
		this.msg_processors = msg_processors;
		
		assert(partition == msg_processors.size());
	}

	@Override
	public boolean init() {
		boolean is_ok = false;
		
		try {
			if (restart_offset_largest) {
				String zk_node = KafkaUtil.getConsumerGroupOffsetZKNode(group_id, topic);
				ZKUtil.deleteNode(zk_list, zk_node);
				logger.info("delete kafka consumer group offset node: {}", zk_node);
			}
			
			consumer = Consumer.createJavaConsumerConnector(
	                KafkaUtil.createConsumerConfig(zk_list, group_id, false));
			Map<String, Integer> topic_partitions = new HashMap<String, Integer>();
			topic_partitions.put(topic, partition);
	        Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumer.createMessageStreams(topic_partitions);
	        List<KafkaStream<byte[], byte[]>> streams = consumerMap.get(topic);
	 
	        //创建Java线程池
	        executor = Executors.newFixedThreadPool(partition);
	 
	        for (int i = 0; i < streams.size(); ++i) {
	        	executor.submit(new HighLevelConsumer(streams.get(i), topic, i, msg_processors.get(i)));
	        }
        	
        	is_ok = true;
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
		}
		
		return is_ok;
	}

	@Override
	public void uninit() {
		if (consumer != null) {
			consumer.shutdown();
			logger.info("Kafka Consumer shutdown");
		}
		
		if (executor != null) {
			executor.shutdown();
			logger.info("Thread Executor shutdown");
		}
	}

}
