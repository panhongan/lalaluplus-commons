package com.github.panhongan.utils.time;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;

/**
 * @author panhongan
 * @since 2020.7.13
 * @version 1.0
 */

public class DateUtils {

    public static final String SETTLE_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static String format(Date date, String pattern) {
        if (Objects.isNull(date)) {
            return null;
        }

        String p = StringUtils.isNotEmpty(pattern) ? pattern : SETTLE_PATTERN;
        return new DateTime(date).toString(p);
    }

	public static void checkFormat(DateTime dateTime, String pattern) {
		if (dateTime.toString(pattern).length() != pattern.length()) {
			throw new RuntimeException("invalid datetime : " + dateTime.toString());
		}
	}

    public static long date2timestamp(Date date) {
        if (Objects.isNull(date)) {
            return 0L;
        }

        return date.toInstant().toEpochMilli();
    }

    /**
     * @param timestamp millseconds
     * @return Date
     */
    public static Date timestamp2date(long timestamp) {
        Instant instant = Instant.ofEpochMilli(timestamp);
        return Date.from(instant);
    }

    public static Date timestamp2date(long seconds, long nanos) {
        Instant instant = Instant.ofEpochSecond(seconds, nanos);
        return Date.from(instant);
    }

    public static Date plusDaysFromNow(int days) {
        return Date.from(LocalDate.now().plusDays(days).atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
}
