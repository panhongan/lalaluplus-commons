package com.github.lalalu.utils.kafka;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.github.lalalu.utils.time.TimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.lalalu.utils.time.TimeUtils;
import com.github.lalalu.utils.fs.PathUtils;

/**
 * lalalu plus
 */
public class MessageLocalWriter extends AbstractKafkaMessageProcessor {
	
	private static final Logger logger = LoggerFactory.getLogger(MessageLocalWriter.class);
	
	private static final int DEFAULT_MINUTES_WINDOW = 1;

	private String data_dir = null;
	
	private int minutes_window = DEFAULT_MINUTES_WINDOW;	// 1 minutes for one file
	
	public MessageLocalWriter(String data_dir, int minutes_window) {
		this(data_dir, "", minutes_window);
	}
	
	public MessageLocalWriter(String data_dir, String name, int minutes_window) {
		super(name);
		
		this.data_dir = data_dir;
		this.minutes_window = minutes_window;
		if (minutes_window <= 0) {
			minutes_window = DEFAULT_MINUTES_WINDOW;
		}
		
		PathUtils.createRecursiveDir(data_dir);
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
		String begin_time = TimeUtils.getTimeSectionByMinute(TimeUtils.currTime(), minutes_window, "yyyyMMdd_HHmm").beginTime;
		String local_path = data_dir;
		PathUtils.createRecursiveDir(local_path);
			
		String local_file = local_path + "/" + topic + "_" + partition_id + "_" + begin_time;
		return local_file;
	}
	
}
