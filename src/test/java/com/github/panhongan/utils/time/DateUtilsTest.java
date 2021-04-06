package com.github.panhongan.utils.time;

import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;

/**
 * @author panhongan
 * @since 2019.7.13
 * @version 1.0
 */
public class DateUtilsTest {

    @Test
    public void testDate2timestamp_Ok() {
        assert(DateUtils.date2timestamp(new Date()) > 0L);
    }

    @Test
    public void testTimestamp2date_Ok() {
        System.out.println(DateUtils.timestamp2date(0L));
    }

    @Test
    public void testPlusDaysFromNow_Ok() {
        assert (DateUtils.plusDaysFromNow(0) != null);
    }

    @Test
    public void testDate2Str_Ok() {
        String str = DateUtils.date2Str(new Date(), "yyyy-MM-dd HH:mm:ss");
        System.out.println(str);
        assert (str.matches("^\\d{4}\\-\\d{2}\\-\\d{2}\\s{1}\\d{2}:\\d{2}:\\d{2}$"));
    }

    @Test
    public void testLocalDate2Str_Ok() {
        String str = DateUtils.localDate2Str(LocalDate.now(), "yyyy-MM-dd");
        System.out.println(str);
        assert (str.matches("^\\d{4}\\-\\d{2}\\-\\d{2}$"));
    }

    @Test
    public void testLocalDateTime2Str_Ok() {
        String str = DateUtils.localDateTime2Str(LocalDateTime.now(), "yyyy-MM-dd HH:mm:ss");
        System.out.println(str);
        assert (str.matches("^\\d{4}\\-\\d{2}\\-\\d{2}\\s{1}\\d{2}:\\d{2}:\\d{2}$"));
    }

    @Test
    public void testStr2Date_Ok() {
        Date date = DateUtils.str2Date("2021-04-01", "yyyy-MM-dd");
        assert (date != null);
    }

    @Test
    public void testStr2LocalDate_Ok() {
        LocalDate localDate = DateUtils.str2LocalDate("2021-04-01", "yyyy-MM-dd");
        assert (localDate != null);
    }

    @Test
    public void testStr2LocalDateTime_Ok() {
        LocalDateTime localDateTime = DateUtils.str2LocalDateTime("2021-04-01 10:00:05", "yyyy-MM-dd HH:mm:ss");
        assert (localDateTime != null);
    }

    @Test
    public void testStr2LocalDateTime_Ok1() {
        LocalDateTime localDateTime = DateUtils.str2LocalDateTime("2021-04-01T10:00:05Z", "yyyy-MM-dd'T'HH:mm:ss'Z'");
        assert (localDateTime != null);
    }

    @Test (expected = Exception.class)
    public void testStr2LocalDateTime_MismatchPatter() {
        LocalDateTime localDateTime = DateUtils.str2LocalDateTime("2021-04-01 10:00:05", "yyyy-MM-ddTHH:mm:ss");
        assert (localDateTime != null);
    }

    @Test
    public void testLocalDate2Date_Ok() {
        LocalDate localDate = LocalDate.now();
        Date date = DateUtils.localDate2Date(localDate);
        assert (Objects.equals(DateUtils.localDate2Str(localDate, "yyyy-MM-dd"),
                DateUtils.date2Str(date, "yyyy-MM-dd")));
    }

    @Test
    public void testLocalDate2Date_Ok1() {
        LocalDate localDate = DateUtils.str2LocalDate("2021-04-01", "yyyy-MM-dd");
        Date date = DateUtils.localDate2Date(localDate, ZoneId.of("UTC"));
        assert (Objects.equals(DateUtils.localDate2Str(localDate, "yyyy-MM-dd"),
                DateUtils.date2Str(date, "yyyy-MM-dd")));
    }
}