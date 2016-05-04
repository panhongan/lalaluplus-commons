package com.github.panhongan.util.mq.kafka;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.panhongan.util.TimeUtil;
import com.github.panhongan.util.path.PathUtil;

public class MessageLocalWriter extends AbstractMessageProcessor {
	
	private static final Logger logger = LoggerFactory.getLogger(MessageLocalWriter.class);

	private String data_dir = null;
	
	public MessageLocalWriter(String data_dir) {
		this(data_dir, "");
	}
	
	public MessageLocalWriter(String data_dir, String name) {
		super(name);
		
		this.data_dir = data_dir;
		PathUtil.createRecursiveDir(data_dir);
	}
	
	public String getDataDir() {
		return data_dir;
	}
	
	@Override
	public boolean init() {
		return (data_dir != null);
	}
	
	@Override
	public void uninit() {
	}

	@Override
	public Object processMessage(String topic, int partition_id, String message) {
		BufferedWriter writer = null;
		
		try {
			String local_file = this.constructFileName(topic, partition_id);
			writer = new BufferedWriter(new FileWriter(local_file, true));
			writer.write(message);
			writer.newLine();
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return true;
	}
	
	@Override
	public Object processMessage(String topic, int partition_id, List<String> msg_list) {
		BufferedWriter writer = null;
		
		try {
			String local_file = this.constructFileName(topic, partition_id);
			writer = new BufferedWriter(new FileWriter(local_file, true));
			for (String message : msg_list) {
				writer.write(message);
				writer.newLine();
			}
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return true;
	}
	
	protected String constructFileName(String topic, int partition_id) {
		String begin_time = TimeUtil.getTimeSectionByMinute(TimeUtil.currTime(), 10, "yyyyMMdd_HHmm").beginTime;
		String local_path = data_dir + "/" + topic;
		PathUtil.createRecursiveDir(local_path);
			
		String local_file = local_path + "/" + topic + "_" + partition_id + "_" + begin_time;
		return local_file;
	}
	
}
