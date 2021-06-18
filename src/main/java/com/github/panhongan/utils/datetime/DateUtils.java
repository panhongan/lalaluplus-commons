package com.github.panhongan.utils.datetime;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import org.joda.time.DateTime;

/**
 * <p>
 * DateUtils
 * </p>
 *
 * @author panhongan
 * @version 1.0
 * @since 2020.7.13
 */

public class DateUtils {

    public static final String SETTLE_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static final String UTC_PATTERN = "yyyy-MM-dd'T'HH:mm:ss'Z'";

    /**
     * check datetime format
     *
     * @param dateTime DateTime
     * @param pattern String
     */
    public static void checkFormat(DateTime dateTime, String pattern) {
        if (dateTime.toString(pattern).length() != pattern.length()) {
            throw new RuntimeException("invalid datetime : " + dateTime.toString());
        }
    }

    public static long date2timestamp(Date date) {
        return date.toInstant().toEpochMilli();
    }

    /**
     * @param timestamp millSeconds
     * @return Date
     */
    public static Date millseconds2date(long timestamp) {
        Instant instant = Instant.ofEpochMilli(timestamp);
        return Date.from(instant);
    }

    public static Date seconds2date(long seconds, long nanos) {
        Instant instant = Instant.ofEpochSecond(seconds, nanos);
        return Date.from(instant);
    }

    public static Date plusDaysFromNow(int days) {
        return Date.from(LocalDate.now().plusDays(days).atStartOfDay(ZoneId.systemDefault())
                .toInstant());
    }

    /**
     * java.util.Date to String
     *
     * @param date Date
     * @param pattern Date format
     * @return Date string
     */
    public static String date2Str(Date date, String pattern) {
        return new DateTime(date).toString(pattern);
    }

    /**
     * java.time.LocalDate to String
     *
     * @param date LocalDate
     * @param pattern Date pattern
     * @return Local date string
     */
    public static String localDate2Str(LocalDate date, String pattern) {
        return date.format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * java.time.LocalDateTime to String
     *
     * @param dateTime LocalDateTime
     * @param pattern Date time pattern
     * @return Local date time string
     */
    public static String localDateTime2Str(LocalDateTime dateTime, String pattern) {
        return dateTime.format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * String to java.util.Date
     *
     * @param dateStr Date string
     * @param datePattern Date format
     * @return Date
     */
    public static Date str2Date(String dateStr, String datePattern) {
        return localDate2Date(str2LocalDate(dateStr, datePattern));
    }

    /**
     * String to java.util.Date
     *
     * @param dateStr Date String
     * @param datePattern Date format
     * @param zoneId ZoneId
     * @return Date
     */
    public static Date str2Date(String dateStr, String datePattern, ZoneId zoneId) {
        return localDate2Date(str2LocalDate(dateStr, datePattern), zoneId);
    }

    /**
     * String to java.time.LocalDate
     *
     * @param dateStr Date string
     * @param datePattern Date format
     * @return LocalDate
     */
    public static LocalDate str2LocalDate(String dateStr, String datePattern) {
        return LocalDate.parse(dateStr, DateTimeFormatter.ofPattern(datePattern));
    }

    /**
     * String to java.time.LocalDateTime
     *
     * @param dateTimeStr Date time string
     * @param dateTimePattern Date time format
     * @return LocalDateTime
     */
    public static LocalDateTime str2LocalDateTime(String dateTimeStr, String dateTimePattern) {
        return LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ofPattern(dateTimePattern));
    }

    /**
     * java.time.LocalDate to java.util.Date
     *
     * @param localDate LocalDate
     * @return Date
     */
    public static Date localDate2Date(LocalDate localDate) {
        return localDate2Date(localDate, ZoneId.systemDefault());
    }

    /**
     * java.time.LocalDate to java.util.Date
     *
     * @param localDate LocalDate
     * @param zoneId ZoneId
     * @return Date
     */
    public static Date localDate2Date(LocalDate localDate, ZoneId zoneId) {
        return Date.from(localDate.atStartOfDay(zoneId).toInstant());
    }
}
