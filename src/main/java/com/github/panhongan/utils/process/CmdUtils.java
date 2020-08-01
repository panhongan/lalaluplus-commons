package com.github.panhongan.utils.process;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.commons.collections4.CollectionUtils;

/**
 * lalalu plus
 */

public class CmdUtils {

	private static final Logger logger = LoggerFactory.getLogger(CmdUtils.class);

	public static boolean exec(String cmd, Collection<String> output, Collection<String> err) {
		boolean ret = false;

		try {
			Runtime runtime = Runtime.getRuntime();
			Process process = runtime.exec(cmd);
			ret = readData(process, output, err);
		} catch (Exception e) {
			logger.warn("", e);
			err.add(e.getMessage());
		}

		return ret;
	}

	public static boolean exec(String[] cmd, Collection<String> output, Collection<String> err) {
		boolean ret = false;

		try {
			Runtime runtime = Runtime.getRuntime();
			Process process = runtime.exec(cmd);
			ret = readData(process, output, err);
		} catch (Exception e) {
			logger.warn(e.getMessage());
			err.add(e.getMessage());
		}

		return ret;
	}

	public static boolean exec(Collection<String> cmd, Collection<String> output, Collection<String> err) {
		if (CollectionUtils.isNotEmpty(cmd)) {
			return exec(cmd.toArray(new String[0]), output, err);
		} else {
			return false;
		}
	}

	private static boolean readData(Process process, Collection<String> output, Collection<String> err) {
		boolean ret = false;

		try {
			ProcessDataReader outputReader = new ProcessDataReader(process.getInputStream(), output);
			outputReader.start();

			ProcessDataReader errReader = new ProcessDataReader(process.getErrorStream(), err);
			errReader.start();

			outputReader.waitForCompletion();
			errReader.waitForCompletion();

			ret = (process.waitFor() == 0);
		} catch (Exception e) {
			logger.warn("", e);
		}

		return ret;
	}

}
