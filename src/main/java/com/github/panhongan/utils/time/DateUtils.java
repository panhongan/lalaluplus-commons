package com.github.panhongan.utils.time;

import org.joda.time.DateTime;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author panhongan
 * @since 2020.7.13
 * @version 1.0
 */

public class DateUtils {

    public static final String SETTLE_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static final String UTC_PATTERN = "yyyy-MM-dd'T'HH:mm:ss'Z'";

	public static void checkFormat(DateTime dateTime, String pattern) {
		if (dateTime.toString(pattern).length() != pattern.length()) {
			throw new RuntimeException("invalid datetime : " + dateTime.toString());
		}
	}

    public static long date2timestamp(Date date) {
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

    /**
     * java.util.Date to String
     */
    public static String date2Str(Date date, String pattern) {
        return new DateTime(date).toString(pattern);
    }

    /**
     * java.time.LocalDate to String
     */
    public static String localDate2Str(LocalDate date, String pattern) {
        return date.format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * java.time.LocalDateTime to String
     */
    public static String localDateTime2Str(LocalDateTime dateTime, String pattern) {
        return dateTime.format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * String to java.util.Date
     */
    public static Date str2Date(String dateStr, String datePattern) {
        return localDate2Date(str2LocalDate(dateStr, datePattern));
    }

    /**
     * String to java.util.Date
     */
    public static Date str2Date(String dateStr, String datePattern, ZoneId zoneId) {
        return localDate2Date(str2LocalDate(dateStr, datePattern), zoneId);
    }

    /**
     * String to java.time.LocalDate
     */
    public static LocalDate str2LocalDate(String dateStr, String datePattern) {
        return LocalDate.parse(dateStr, DateTimeFormatter.ofPattern(datePattern));
    }

    /**
     * String to java.time.LocalDateTime
     */
    public static LocalDateTime str2LocalDateTime(String dateTimeStr, String dateTimePattern) {
        return LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ofPattern(dateTimePattern));
    }

    /**
     * java.time.LocalDate to java.util.Date
     */
    public static Date localDate2Date(LocalDate localDate) {
        return localDate2Date(localDate, ZoneId.systemDefault());
    }

    /**
     * java.time.LocalDate to java.util.Date
     */
    public static Date localDate2Date(LocalDate localDate, ZoneId zoneId) {
        return Date.from(localDate.atStartOfDay(zoneId).toInstant());
    }
}
