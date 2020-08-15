package com.github.panhongan.utils.time;

import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import java.util.Date;

/**
 * lalalu plus
 */
public class DateUtils {

    public static final String SETTLE_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static String format(Date date, String pattern) {
        Preconditions.checkNotNull(date);
        Preconditions.checkArgument(StringUtils.isNotEmpty(pattern));

        return new DateTime(date).toString(pattern);
    }

	public static void checkFormat(DateTime dateTime, String pattern) {
		if (dateTime.toString(pattern).length() != pattern.length()) {
			throw new RuntimeException("invalid datetime : " + dateTime.toString());
		}
	}
}
