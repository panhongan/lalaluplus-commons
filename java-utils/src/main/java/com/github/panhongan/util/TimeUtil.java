package com.github.panhongan.util;

import java.text.SimpleDateFormat;
import java.util.Date;


public class TimeUtil {
	
	public static long currTime() {
		return System.currentTimeMillis() / 1000;
	}
	
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
			return sdf.parse(date).getTime() / 1000;
		} catch (Exception e) {
		}

		return -1;
	}
	
	public static String getTime(String format, int minuteDelt) {
		Date nowTime = new Date();
		nowTime.setTime(nowTime.getTime() + minuteDelt * 60000);
		SimpleDateFormat time = new SimpleDateFormat(format);
		return time.format(nowTime);
	}
	
	public static long getTime(int minuteDelt) {
		return (new Date().getTime() + minuteDelt * 60000) / 1000;
	}
	
	public static TimeSection getTimeSectionByMinute(long time, int minuteInterval, String format) {
		String originalTime = TimeUtil.secToDate(time, "yyyy-MM-dd HH:mm:ss");
		int beginMinute = (Integer.valueOf(originalTime.substring(14, 16)).intValue() / minuteInterval) * minuteInterval;
		String beginTime = originalTime.substring(0, 14) + String.format("%02d:00", beginMinute);
		long beginTimeSec = TimeUtil.dateToSec(beginTime, "yyyy-MM-dd HH:mm:ss");
		String finalBeginTime = TimeUtil.secToDate(beginTimeSec, format);
		
		int endMinute = (Integer.valueOf(originalTime.substring(14, 16)).intValue() / minuteInterval + 1) * minuteInterval;
		String endTime = originalTime.substring(0, 14) + String.format("%02d:00", endMinute);
		long endTimeSec = TimeUtil.dateToSec(endTime, "yyyy-MM-dd HH:mm:ss");
		String finalEndTime = TimeUtil.secToDate(endTimeSec, format);
		
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
