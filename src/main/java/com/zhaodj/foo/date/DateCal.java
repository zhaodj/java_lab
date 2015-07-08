package com.zhaodj.foo.date;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import ch.qos.logback.core.joran.action.NewRuleAction;


public class DateCal {
	
	private static Date calExpireTime(Date startTime,int len,String unit){
		if("y".equals(unit)){
			return DateUtils.addYears(startTime, len);
		}else if("m".equals(unit)){
			return DateUtils.addMonths(startTime, len);
		}
		throw new IllegalArgumentException();
	}
	
	private static int calRewardNum(Date now,Date start) {
		//Date nowDate=now;
		//Date startDate=start;
		Date nowDate=DateUtils.truncate(now, Calendar.DATE);
		Date startDate=DateUtils.truncate(start, Calendar.DATE);
		int range = (int)(((nowDate.getTime()-startDate.getTime())/86400000)%30);
		//System.out.println(range);
		if(range<=9) {
			return 15;
		}else if(range>9 && range<=19) {
			return 20;
		}
		return 25;
	}
	
	private static void testCalRewardNum(Date now, Date start) {
		Date end = DateUtils.addDays(now, 1);
		end=DateUtils.truncate(end, Calendar.DATE);
		while(now.compareTo(end)<=0) {
			System.out.println(DateFormatUtils.format(now, "yyyy-MM-dd HH:mm:ss") + ": " + calRewardNum(now, start));
			now=DateUtils.addHours(now, 1);
		}
	}
	
	public static void main(String[] args) throws ParseException{
		Date date = new Date();
		System.out.println(calExpireTime(date,1,"m"));
		System.out.println(calExpireTime(date,2,"m"));
		System.out.println(calExpireTime(date,1,"y"));
		Date startDate = DateUtils.addDays(date, -25);
		System.out.println(calRewardNum(date, startDate));
		Calendar calendar = Calendar.getInstance();
		System.out.println(calendar.get(Calendar.WEEK_OF_YEAR));
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		System.out.println(DateFormatUtils.format(calendar, "yyyy-MM-dd HH:mm:ss"));
		System.out.println(DateFormatUtils.format(getNowWeekMonday(calendar.getTime()), "yyyy-MM-dd HH:mm:ss"));
		Date now=DateUtils.parseDate("2014-10-10 00:00:00", "yyyy-MM-dd HH:mm:ss");
		Date start=DateUtils.parseDate("2014-10-01 09:43:09", "yyyy-MM-dd HH:mm:ss");
		testCalRewardNum(now, start);
	}
	
	public static Date getNowWeekMonday(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_MONTH, -1); 
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		return DateUtils.truncate(cal.getTime(), Calendar.DATE);
	}


}
