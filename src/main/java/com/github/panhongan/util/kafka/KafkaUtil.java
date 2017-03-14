package com.github.panhongan.util.kafka;

import kafka.admin.AdminUtils;
import kafka.api.PartitionOffsetRequestInfo;
import kafka.common.TopicAndPartition;
import kafka.consumer.ConsumerConfig;
import kafka.javaapi.OffsetRequest;
import kafka.javaapi.OffsetResponse;
import kafka.javaapi.PartitionMetadata;
import kafka.javaapi.TopicMetadata;
import kafka.javaapi.TopicMetadataRequest;
import kafka.javaapi.TopicMetadataResponse;
import kafka.javaapi.consumer.SimpleConsumer;
import kafka.utils.ZKStringSerializer$;
import kafka.utils.ZkUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.ZkConnection;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.panhongan.util.conf.Config;
import com.github.panhongan.util.zookeeper.ZKUtil;

public class KafkaUtil {

	private static Logger logger = LoggerFactory.getLogger(KafkaUtil.class);

	private static final long INVALID_OFFSET = -1;

	private static final int DEFAULT_PORT = 9092;

	private static final int DEFAULT_TIMEOUT = 100000; // millseconds

	private static final int DEFAULT_BUFFER_SIZE = 64 * 1024;

	private static final String DEFAULT_CLIENT_NAME = "default_client";

	public static Map<Integer, Long> getLastestWriteOffset(String broker_list, String topic) {
		Map<Integer, Long> map = new HashMap<Integer, Long>();

		try {
			List<String> broker_hosts = new ArrayList<String>();
			int port = DEFAULT_PORT;
			String [] arr = broker_list.split("[,]");
			for (int i = 0; i < arr.length; ++i) {
				String [] host_port = arr[i].split("[:]");
				broker_hosts.add(host_port[0]);
				port = Integer.valueOf(host_port[1]);
			}

			List<PartitionMetadata> partition_metadatas = KafkaUtil.findTopicPartitionMetadata(broker_hosts, port, topic);
			if (partition_metadatas != null) {
				for (PartitionMetadata part : partition_metadatas) {
					long last_offset = KafkaUtil.getLastestWriteOffset(part.leader().host(), port, topic,
							part.partitionId());
					map.put(part.partitionId(), last_offset);
				}
			}
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
		}

		return map;
	}

	public static long getLastestWriteOffset(String broker_leader, int port, String topic, int partition) {
		long ret = INVALID_OFFSET;
		SimpleConsumer consumer = null;

		try {
			consumer = new SimpleConsumer(broker_leader, port, DEFAULT_TIMEOUT, DEFAULT_BUFFER_SIZE,
					DEFAULT_CLIENT_NAME);

			Map<TopicAndPartition, PartitionOffsetRequestInfo> requestInfo = new HashMap<TopicAndPartition, PartitionOffsetRequestInfo>();
			requestInfo.put(new TopicAndPartition(topic, partition),
					new PartitionOffsetRequestInfo(kafka.api.OffsetRequest.LatestTime(), 1));
			OffsetRequest request = new OffsetRequest(requestInfo,
					kafka.api.OffsetRequest.CurrentVersion(), DEFAULT_CLIENT_NAME);
			OffsetResponse response = consumer.getOffsetsBefore(request);
			if (response.hasError()) {
				logger.error("failed to fetch data offset, err : {}", response.errorCode(topic, partition));
			} else {
				long[] offsets = response.offsets(topic, partition);
				ret = offsets[0];
			}
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
		} finally {
			KafkaUtil.closeSimpleConsumer(consumer);
		}

		return ret;
	}

	public static TopicMetadata findTopicMetadata(List<String> broker_hosts, int port, String topic) {
		TopicMetadata topic_metadata = null;

		for (String broker : broker_hosts) {
			SimpleConsumer consumer = null;

			try {
				consumer = new SimpleConsumer(broker, port, DEFAULT_TIMEOUT, DEFAULT_BUFFER_SIZE, DEFAULT_CLIENT_NAME);
				List<String> topics = Collections.singletonList(topic);
				TopicMetadataRequest req = new TopicMetadataRequest(topics);
				TopicMetadataResponse resp = consumer.send(req);
				List<TopicMetadata> topic_metas = resp.topicsMetadata();
				if (!topic_metas.isEmpty()) {
					topic_metadata = topic_metas.get(0);
					break;
				}
			} catch (Exception e) {
				logger.warn(e.getMessage());
			} finally {
				KafkaUtil.closeSimpleConsumer(consumer);
			}
		}

		return topic_metadata;
	}

	public static List<PartitionMetadata> findTopicPartitionMetadata(List<String> broker_hosts, int port,
			String topic) {
		List<PartitionMetadata> partition_metadata_list = null;

		TopicMetadata topic_metadata = KafkaUtil.findTopicMetadata(broker_hosts, port, topic);
		if (topic_metadata != null) {
			partition_metadata_list = topic_metadata.partitionsMetadata();
		}

		return partition_metadata_list;
	}

	public static PartitionMetadata findTopicPartitionMetadata(List<String> broker_hosts, int port, String topic,
			int partition) {
		PartitionMetadata partition_meta_data = null;

		List<PartitionMetadata> partition_metadata_list = KafkaUtil.findTopicPartitionMetadata(broker_hosts, port,
				topic);
		if (partition_metadata_list != null) {
			for (PartitionMetadata meta_data : partition_metadata_list) {
				if (meta_data.partitionId() == partition) {
					partition_meta_data = meta_data;
					break;
				}
			}
		}

		return partition_meta_data;
	}

	public static void closeSimpleConsumer(SimpleConsumer consumer) {
		if (consumer != null) {
			try {
				consumer.close();
			} catch (Exception e) {
			}
		}
	}

	public static Producer<String, String> createProducer(Config producer_config) {
		Properties props = new Properties();
		
		props.put("bootstrap.servers", producer_config.getString("bootstrap.servers"));
		props.put("metadata.broker.list", producer_config.getString("bootstrap.servers"));
		props.put("zookeeper.connect", producer_config.getString("zookeeper.connect"));
		props.put("producer.type", producer_config.getString("producer.type", "async"));
		props.put("acks", producer_config.getString("acks", "all"));
		props.put("block.on.buffer.full", producer_config.getString("block.on.buffer.full", "true"));
		props.put("max.in.flight.requests.per.connection", producer_config.getString("max.in.flight.requests.per.connection", "1"));
		props.put("retries", producer_config.getString("retries", "2"));
		props.put("key.serializer", producer_config.getString("key.serializer", "org.apache.kafka.common.serialization.StringSerializer"));
		props.put("value.serializer", producer_config.getString("value.serializer", "org.apache.kafka.common.serialization.StringSerializer"));

		return new KafkaProducer<String, String>(props);
	}

	public static void closeProducer(Producer<String, String> producer) {
		if (producer != null) {
			try {
				producer.close();
			} catch (Exception e) {
			}
		}
	}

	public static boolean sendSync(Producer<String, String> producer, String topic, String message) {
		boolean ret = false;
		
		try {
			RecordMetadata result = producer.send(new ProducerRecord<String, String>(topic, message)).get();
			logger.info("kafka producer send result : " + result);
			ret = (result != null);
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
		}
		
		return ret;
	}
	
	public static void sendAsync(Producer<String, String> producer, String topic, String message, Callback callback) {
		try {
			producer.send(new ProducerRecord<String, String>(topic, message), callback);
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
		}
	}

	// old 
	public static ConsumerConfig createConsumerConfig(Config config) {
		Properties props = new Properties();
		props.put("zookeeper.connect", config.getString("zookeeper.connect"));
		props.put("group.id", config.getString("group.id"));
		props.put("auto.offset.reset", config.getString("auto.offset.reset", "largest"));
		props.put("enable.auto.commit", config.getString("enable.auto.commit", "true"));
		props.put("auto.commit.interval.ms", config.getString("auto.commit.interval.ms", "1000"));
		return new ConsumerConfig(props);
	}

	public static String getConsumerGroupOwnerZKNode(String groupid, String topic) {
		return "/consumers/" + groupid + "/owners/" + topic;
	}

	public static String getConsumerGroupOffsetZKNode(String groupid, String topic) {
		return "/consumers/" + groupid + "/offsets/" + topic;
	}
	
	public static boolean isKafkaClusterAlive(String zk_list) {
		boolean is_alive = false;
		
		try {
			ZooKeeper zk = ZKUtil.connectZK(zk_list, 10 * 1000, null);
			is_alive = !zk.getChildren("/brokers/ids", false).isEmpty();
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
		}
		
		return is_alive;
	}
	
	public static void createTargetTopics(String topic, int partitions, int replicas, String zk_list) {
		ZkClient zkClient = null;
		ZkUtils zkUtils = null;
		
		try {
			zkClient = new ZkClient(zk_list, 15 * 1000, 10 * 1000, ZKStringSerializer$.MODULE$);
			zkUtils = new ZkUtils(zkClient, new ZkConnection(zk_list), false);
			AdminUtils.createTopic(zkUtils, topic, partitions, replicas, new Properties());
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (zkClient != null) {
				zkClient.close();
			}
			
			if (zkUtils != null) {
				zkUtils.close();
			}
		}
	}

}
