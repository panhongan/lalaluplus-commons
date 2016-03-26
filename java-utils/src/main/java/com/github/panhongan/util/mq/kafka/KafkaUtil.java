package com.github.panhongan.util.mq.kafka;

import kafka.api.PartitionOffsetRequestInfo;
import kafka.common.TopicAndPartition;
import kafka.consumer.ConsumerConfig;
import kafka.javaapi.*;
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
 
    public static long getLastOffset(SimpleConsumer consumer, String topic, int partition,
                                     long whichTime, String clientName) {
        TopicAndPartition topicAndPartition = new TopicAndPartition(topic, partition);
        Map<TopicAndPartition, PartitionOffsetRequestInfo> requestInfo = new HashMap<TopicAndPartition, PartitionOffsetRequestInfo>();
        requestInfo.put(topicAndPartition, new PartitionOffsetRequestInfo(whichTime, 1));
        kafka.javaapi.OffsetRequest request = new kafka.javaapi.OffsetRequest(
                requestInfo, kafka.api.OffsetRequest.CurrentVersion(), clientName);
        OffsetResponse response = consumer.getOffsetsBefore(request);
 
        if (response.hasError()) {
            logger.error("Error fetching data Offset Data the Broker. Reason: {}", 
            		response.errorCode(topic, partition) );
            return 0;
        }
        long[] offsets = response.offsets(topic, partition);
        return offsets[0];
    }
 
    public static String findNewLeader(List<String> a_seedBrokers, String a_oldLeader, 
    		String a_topic, int a_partition, int a_port) throws Exception {
        for (int i = 0; i < 3; i++) {
            boolean goToSleep = false;
            PartitionMetadata metadata = findLeader(a_seedBrokers, a_port, a_topic, a_partition);
            if (metadata == null) {
                goToSleep = true;
            } else if (metadata.leader() == null) {
                goToSleep = true;
            } else if (a_oldLeader.equalsIgnoreCase(metadata.leader().host()) && i == 0) {
                goToSleep = true;
            } else {
                return metadata.leader().host();
            }
            if (goToSleep) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ie) {
                }
            }
        }
        logger.error("Unable to find new leader after Broker failure. Exiting");
        throw new Exception("Unable to find new leader after Broker failure. Exiting");
    }
 
    public static PartitionMetadata findLeader(List<String> a_seedBrokers, int a_port, String a_topic, int a_partition) {
        PartitionMetadata returnMetaData = null;
        loop:
        for (String seed : a_seedBrokers) {
            SimpleConsumer consumer = null;
            try {
                consumer = new SimpleConsumer(seed, a_port, 100000, 64 * 1024, "leaderLookup");
                List<String> topics = Collections.singletonList(a_topic);
                TopicMetadataRequest req = new TopicMetadataRequest(topics);
                kafka.javaapi.TopicMetadataResponse resp = consumer.send(req);
 
                List<TopicMetadata> metaData = resp.topicsMetadata();
                for (TopicMetadata item : metaData) {
                    for (PartitionMetadata part : item.partitionsMetadata()) {
                        if (part.partitionId() == a_partition) {
                            returnMetaData = part;
                            break loop;
                        }
                    }
                }
            } catch (Exception e) {
                logger.warn(e.getMessage());
            } finally {
                if (consumer != null) consumer.close();
            }
        }

        return returnMetaData;
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
    
    public static ConsumerConfig createConsumerConfig(String zk_list, String group_id) {
        Properties props = new Properties();
        props.put("zookeeper.connect", zk_list);
        props.put("group.id", group_id);
        props.put("zookeeper.session.timeout.ms", "30000");
        props.put("zookeeper.sync.time.ms", "2000");
        props.put("auto.commit.interval.ms", "1000");
        return new ConsumerConfig(props);
    }
    
}