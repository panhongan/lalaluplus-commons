package com.github.panhongan.util;

import com.github.panhongan.util.TimeUtil;
import com.github.panhongan.util.TimeUtil.TimeSection;

public class TestTimeUtil {
	
	public static void main(String [] args) {
		System.out.println(TimeUtil.secToDate(1436328454L, "yyyy-MM-dd HH:mm:ss"));
		System.out.println(TimeUtil.currTime("yyyy-MM-dd HH:mm:ss"));
		System.out.println(TimeUtil.dateToSec("2015-11-08 23:52:57", "yyyy-MM-dd HH:mm:ss"));
		
		String time = TimeUtil.getTime("yyyy-MM-dd HH:mm:ss", -10);
		System.out.println(time);
		
		long curr_time = TimeUtil.currTime();
		System.out.println(curr_time);
		
		TimeSection sec = TimeUtil.getTimeSectionByMinute(curr_time, 10, "yyyy-MM-dd HH:mm:ss");
		System.out.println(sec.beginTime);
		System.out.println(sec.endTime);
	}

}
