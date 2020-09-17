package com.github.panhongan.utils.time;

import org.junit.Test;

import java.util.Date;

/**
 * @author panhongan
 * @since 2019.7.13
 * @version 1.0
 */

public class DateUtilsTest {

    @Test
    public void testFormat_DateIsNull() {
        DateUtils.format(null, "");
    }

    @Test
    public void testFormat_PatternIsEmpty() {
        DateUtils.format(new Date(), "");
    }

    @Test
    public void testFormat_Ok() {
        assert(DateUtils.format(new Date(), DateUtils.SETTLE_PATTERN) != null);
    }

    @Test
    public void testDate2timestamp_Ok() {
        assert(DateUtils.date2timestamp(new Date()) > 0L);
    }

    @Test
    public void testTimestamp2date_Ok() {
        System.out.println(DateUtils.timestamp2date(0L));
    }
}
