package com.github.panhongan.utils.process;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

/**
 * lalalu plus
 */

class ProcessDataReader extends Thread {

	private static final Logger logger = LoggerFactory.getLogger(ProcessDataReader.class);

	private Collection<String> dataList;

	private InputStream inputStream;

	public ProcessDataReader(InputStream inputStream, Collection<String> list) {
		this.inputStream = inputStream;
		this.dataList = list;
		Preconditions.checkNotNull(inputStream);
		Preconditions.checkNotNull(list);
	}

	@Override
	public void run() {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
			String line;
			while ((line = br.readLine()) != null) {
				if (StringUtils.isNotEmpty(line)) {
					dataList.add(line);
				}
			}
		} catch (Exception e) {
			logger.warn("", e);
		}
	}

	public void waitForCompletion() {
		try {
			this.join();
		} catch (Exception e) {
			logger.warn("", e);
		}
	}

}
