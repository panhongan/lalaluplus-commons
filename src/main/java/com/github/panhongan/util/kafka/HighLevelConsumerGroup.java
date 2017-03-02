package com.github.panhongan.util.kafka;

import com.github.panhongan.util.conf.Config;
import com.github.panhongan.util.control.Lifecycleable;
import com.github.panhongan.util.zookeeper.ZKUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kafka.consumer.Consumer;
import kafka.consumer.KafkaStream;
import kafka.consumer.Whitelist;
import kafka.javaapi.consumer.ConsumerConnector;

public class HighLevelConsumerGroup implements Lifecycleable {
	
	private static final Logger logger = LoggerFactory.getLogger(HighLevelConsumerGroup.class);
	
	private List<AbstractMessageProcessor> msg_processors = null;
	
	private List<ConsumerConnector> connectors = new ArrayList<ConsumerConnector>();
	
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
			
			//创建Java线程池
	        executor = Executors.newFixedThreadPool(partition);
	        
	        Config consumer_config = new Config();
	        consumer_config.addProperty("zookeeper.connect", zk_list);
	        consumer_config.addProperty("group.id", group_id);
	        consumer_config.addProperty("auto.offset.reset", restart_offset_largest ? "largest" : "smallest");
	        
			for (int i = 0; i < partition; ++i) {
				ConsumerConnector connector = Consumer.createJavaConsumerConnector(KafkaUtil.createConsumerConfig(consumer_config));
				connectors.add(connector);
				
				KafkaStream<byte[], byte[]> stream = connector.createMessageStreamsByFilter(new Whitelist(topic), 1).get(0);
				executor.submit(new HighLevelConsumer(stream, topic, msg_processors.get(i)));
			}
        	
        	is_ok = true;
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
		}
		
		return is_ok;
	}

	@Override
	public void uninit() {
		for (ConsumerConnector connector : connectors) {
			connector.shutdown();
		}
		logger.info("Consumer Connectors shutdown");
		
		if (executor != null) {
			executor.shutdown();
			logger.info("Thread Executor shutdown");
		}
	}

}
