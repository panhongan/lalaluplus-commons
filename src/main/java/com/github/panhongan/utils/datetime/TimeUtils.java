package com.github.panhongan.utils.datetime;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author lalaluplus
 * @since 2021.7.13
 */
public class TimeUtils {

    public static String currTime(String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date());
    }

    public static String secToDate(long secs, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(secs * 1000L));
    }

    public static long dateToSec(String date, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.parse(date).getTime() / 1000L;
        } catch (Exception var3) {
            return -1L;
        }
    }

    public static String getTime(String format, int minuteDelta) {
        Date nowTime = new Date();
        nowTime.setTime(nowTime.getTime() + (minuteDelta * 60 * 1000L));
        SimpleDateFormat time = new SimpleDateFormat(format);
        return time.format(nowTime);
    }

    public static long getTime(int minuteDelta) {
        return ((new Date()).getTime() + (minuteDelta * 60 * 1000L)) / 1000L;
    }

    public static TimeSection getTimeSectionByMinute(long seconds, int minuteInterval, String format) {
        String originalTime = secToDate(seconds, "yyyy-MM-dd HH:mm:ss");
        int beginMinute = Integer.valueOf(originalTime.substring(14, 16)) / minuteInterval * minuteInterval;
        String beginTime = originalTime.substring(0, 14) + String.format("%02d:00", beginMinute);
        long beginTimeSec = dateToSec(beginTime, "yyyy-MM-dd HH:mm:ss");
        String finalBeginTime = secToDate(beginTimeSec, format);
        int endMinute = (Integer.valueOf(originalTime.substring(14, 16)) / minuteInterval + 1) * minuteInterval;
        String endTime = originalTime.substring(0, 14) + String.format("%02d:00", endMinute);
        long endTimeSec = dateToSec(endTime, "yyyy-MM-dd HH:mm:ss");
        String finalEndTime = secToDate(endTimeSec, format);
        return new TimeSection(finalBeginTime, finalEndTime);
    }

    public static class TimeSection {
        public String beginTime;
        public String endTime;

        public TimeSection(String beginTime, String endTime) {
            this.beginTime = beginTime;
            this.endTime = endTime;
        }
    }
}
