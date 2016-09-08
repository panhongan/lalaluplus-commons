package com.github.panhongan.util.hadoop;

import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.io.Text;

public class FirstKeyIntPartitioner  extends org.apache.hadoop.mapreduce.Partitioner<Text, Text> 
	implements org.apache.hadoop.mapred.Partitioner<Text, Text> {
	
	@Override
	public int getPartition(Text key, Text value, int numReduceTasks) {
		return Integer.valueOf(key.toString()).intValue();
	}

	@Override
	public void configure(JobConf arg0) {
		// do nothing
	}

}
