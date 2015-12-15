package pha.java.util;

import java.text.SimpleDateFormat;
import java.util.Date;


public class TimeUtil {
	
	public static long currSecond() {
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
	
	public static long DateToSec(String date, String format) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.parse(date).getTime() / 1000;
		} catch (Exception e) {
		}

		return -1;
	}
	
	public static void main(String [] args) {
		System.out.println(TimeUtil.secToDate(1436328454L, "yyyy-MM-dd HH:mm:ss"));
		System.out.println(TimeUtil.currTime("yyyy-MM-dd HH:mm:ss"));
		System.out.println(TimeUtil.DateToSec("2015-11-08 23:52:57", "yyyy-MM-dd HH:mm:ss"));
	}

}
