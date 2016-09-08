package com.github.panhongan.util.process;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


class ProcessDataReader extends Thread {

	private static final Logger logger = LoggerFactory.getLogger(ProcessDataReader.class);

	private List<String> dataList = null;

	private InputStream inputStream = null;

	public ProcessDataReader(InputStream inputStream, List<String> list) {
		this.inputStream = inputStream;
		this.dataList = list;
		assert (inputStream != null);
	}

	@Override
	public void run() {
		BufferedReader br = null;

		try {
			br = new BufferedReader(new InputStreamReader(inputStream));
			String line = null;
			while ((line = br.readLine()) != null) {
				if (dataList != null) {
					dataList.add(line);
				}
			}
		} catch (Exception e) {
			logger.warn(e.getMessage());
		} finally {
			try {
				if (br != null) {
					br.close();
				}

			} catch (Exception e) {
				logger.warn(e.getMessage());
			}

			try {
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (Exception e) {
				logger.warn(e.getMessage());
			}
		}
	}

	public void waitFor() {
		try {
			this.join();
		} catch (Exception e) {
			logger.warn(e.getMessage());
		}
	}

}
