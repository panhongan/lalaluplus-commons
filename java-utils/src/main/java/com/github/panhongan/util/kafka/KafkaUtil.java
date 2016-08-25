package com.github.panhongan.util.kafka;

import kafka.api.PartitionOffsetRequestInfo;
import kafka.common.TopicAndPartition;
import kafka.consumer.ConsumerConfig;
import kafka.javaapi.OffsetResponse;
import kafka.javaapi.PartitionMetadata;
import kafka.javaapi.TopicMetadata;
import kafka.javaapi.TopicMetadataRequest;
import kafka.javaapi.consumer.SimpleConsumer;
import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerClosedException;
import kafka.producer.ProducerConfig;
import scala.MatchError;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class KafkaUtil {
	
	private static Logger logger = LoggerFactory.getLogger(KafkaUtil.class);
	
	private static final long INVALID_OFFSET = -1;
	
	private static final int DEFAULT_TIMEOUT = 100000;	// millseconds
	
	private static final int DEFAULT_BUFFER_SIZE = 64 * 1024;
	
	private static final String DEFAULT_CLIENT_NAME = "default_client";
 
	
	public static Map<Integer, Long> getLastOffset(List<String> broker_hosts, int port, String topic) {
		Map<Integer, Long> map = new HashMap<Integer, Long>();
		
		List<PartitionMetadata> partition_metadatas = KafkaUtil.findTopicPartitionMetadata(broker_hosts, port, topic);
        if (partition_metadatas != null) {
        	for (PartitionMetadata part : partition_metadatas) {
        		long last_offset = KafkaUtil.getLastOffset(part.leader().host(), port, topic, part.partitionId());
        		map.put(part.partitionId(), last_offset);
        	}
        }
        
        return map;
	}
	
    public static long getLastOffset(String broker_leader, int port, String topic, int partition) {
    	long ret = INVALID_OFFSET;
    	SimpleConsumer consumer = null;
    	
    	try {
	        consumer = new SimpleConsumer(broker_leader, port, 
	        		DEFAULT_TIMEOUT, DEFAULT_BUFFER_SIZE, DEFAULT_CLIENT_NAME);
	        
	        Map<TopicAndPartition, PartitionOffsetRequestInfo> requestInfo = 
	        		new HashMap<TopicAndPartition, PartitionOffsetRequestInfo>();
	        requestInfo.put(new TopicAndPartition(topic, partition),
	        		new PartitionOffsetRequestInfo(kafka.api.OffsetRequest.LatestTime(), 1));
	        kafka.javaapi.OffsetRequest request = new kafka.javaapi.OffsetRequest(
	                requestInfo, kafka.api.OffsetRequest.CurrentVersion(), DEFAULT_CLIENT_NAME);
	        OffsetResponse response = consumer.getOffsetsBefore(request);
	        if (response.hasError()) {
	            logger.error("failed to fetch data offset, err : {}", 
	            		response.errorCode(topic, partition) );
	        } else {
	        	long [] offsets = response.offsets(topic, partition);
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
                consumer = new SimpleConsumer(broker, port, DEFAULT_TIMEOUT, 
                		DEFAULT_BUFFER_SIZE, DEFAULT_CLIENT_NAME);
                List<String> topics = Collections.singletonList(topic);
                TopicMetadataRequest req = new TopicMetadataRequest(topics);
                kafka.javaapi.TopicMetadataResponse resp = consumer.send(req);
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
    
    public static List<PartitionMetadata> findTopicPartitionMetadata(List<String> broker_hosts, int port, String topic) {
    	List<PartitionMetadata> partition_metadata_list = null;
    	
    	TopicMetadata topic_metadata = KafkaUtil.findTopicMetadata(broker_hosts, port, topic);
    	if (topic_metadata != null) {
    		partition_metadata_list = topic_metadata.partitionsMetadata();
    	}
    	
    	return partition_metadata_list;
    }
    
    public static PartitionMetadata findTopicPartitionMetadata(List<String> broker_hosts, int port, 
    		String topic, int partition) {
    	PartitionMetadata partition_meta_data = null;
    	
    	List<PartitionMetadata> partition_metadata_list = 
    			KafkaUtil.findTopicPartitionMetadata(broker_hosts, port, topic);
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
    		} catch (Exception e){
    		}
    	}
    }
    
    public static Producer<String, String> createProducer(String zkList, String kafkaList, boolean sync) {
		Properties props = new Properties();
		props.put("zookeeper.connect", zkList);
		props.put("serializer.class", "kafka.serializer.StringEncoder");
		props.put("metadata.broker.list", kafkaList);
		props.put("producer.type", sync ? "sync" : "async");
		props.put("request.required.acks", "-1");
		
		return new Producer<String, String>(new ProducerConfig(props));
    }
    
    public static void closeProducer(Producer<String, String> producer) {
    	if (producer != null) {
    		try {
    			producer.close();
    		} catch (Exception e) {
    		}
    	}
    }
    
    public static int sendBatch(Producer<String, String> producer, String topic, List<String> datas) {
    	int ret = -1;
		
		try {
			List<KeyedMessage<String, String>> dataToSend = new ArrayList<KeyedMessage<String, String>>();
			
			for (String data : datas) {
				dataToSend.add(new KeyedMessage<String, String>(topic, data));
			}
			
			producer.send(dataToSend);
			ret = 0;
		} catch (Exception e) {
			logger.warn(e.getMessage());
			e.printStackTrace();
			
			if (e instanceof ProducerClosedException) {
				ret = 1;
			} else if (e instanceof MatchError) {
				ret = 2;
			}
		}
		
		return ret;
    }
    
    public static int sendData(Producer<String, String> producer, String topic, String data) {
		List<String> datas = new ArrayList<String>();
		datas.add(data);
		
		return KafkaUtil.sendBatch(producer, topic, datas);
    }
    
    public static ConsumerConfig createConsumerConfig(String zk_list, String group_id, boolean from_beginning) {
        Properties props = new Properties();
        props.put("zookeeper.connect", zk_list);
        props.put("group.id", group_id);
        props.put("auto.offset.reset", (from_beginning ? "smallest" : "largest"));
        props.put("zookeeper.session.timeout.ms", "30000");
        props.put("zookeeper.sync.time.ms", "2000");
        props.put("auto.commit.interval.ms", "1000");
        return new ConsumerConfig(props);
    }
    
    public static String getConsumerGroupOwnerZKNode(String groupid, String topic) {
    	return "/consumers/" + groupid + "/owners/" + topic;
    }
    
    public static String getConsumerGroupOffsetZKNode(String groupid, String topic) {
    	return "/consumers/" + groupid + "/offsets/" + topic;
    }
     
}
