package com.github.lalalu.utils.time;

import com.github.lalalu.utils.time.TimeUtils;
import com.github.lalalu.utils.time.TimeUtils.TimeSection;

/**
 * lalalu plus
 */
public class TestTimeUtils {
	
	public static void main(String [] args) {
		System.out.println(TimeUtils.secToDate(1436328454L, "yyyy-MM-dd HH:mm:ss"));
		System.out.println(TimeUtils.currTime("yyyy-MM-dd HH:mm:ss"));
		System.out.println(TimeUtils.dateToSec("2015-11-08 23:52:57", "yyyy-MM-dd HH:mm:ss"));
		
		String time = TimeUtils.getTime("yyyy-MM-dd HH:mm:ss", -10);
		System.out.println(time);
		
		long curr_time = TimeUtils.currTime();
		System.out.println(curr_time);
		
		TimeUtils.TimeSection sec = TimeUtils.getTimeSectionByMinute(curr_time, 10, "yyyy-MM-dd HH:mm:ss");
		System.out.println(sec.beginTime);
		System.out.println(sec.endTime);
	}

}
