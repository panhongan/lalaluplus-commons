package com.github.lalalu.utils.time;

import org.joda.time.DateTime;

/**
 * lalalu plus
 */
public class DateUtils {

	public static void checkFormat(DateTime dateTime, String pattern) {
		if (dateTime.toString(pattern).length() != pattern.length()) {
			throw new RuntimeException("invalid datetime : " + dateTime.toString());
		}
	}

}
