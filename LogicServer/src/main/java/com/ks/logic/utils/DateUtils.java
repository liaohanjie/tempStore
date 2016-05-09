package com.ks.logic.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2015年9月15日
 */
public class DateUtils {

	/**
	 * 计算日差, 按天算，忽略时间 (date1-date2)
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int dateDiff(Date date1, Date date2){
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(date1);
		c2.setTime(date2);
		return c1.get(Calendar.DAY_OF_YEAR) - c2.get(Calendar.DAY_OF_YEAR);
	}
	
	/**
	 * 获取下周一的时间
	 * 
	 * @return
	 */
	public static Date getNextMonday() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		cal.add(Calendar.WEEK_OF_YEAR, 1);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // 格式化日期

		String firstDayOfWeek = sdf.format(cal.getTime()) + " 00:00:00";
		try {
			return sdf.parse(firstDayOfWeek);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * 返回当日指定时间
	 * @param hour
	 * @param minute
	 * @param second
	 * @return
	 */
	public static Calendar getCurrentDate(int hour, int minute, int second) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE,minute);
		calendar.set(Calendar.SECOND, second);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar;
	}
	
	/**
	 * 计算两个时间之间相差的天 
	 * @param time1 时间1
	 * @param time2 时间2
	 * @param borderHour 以这个小时数区分天
	 * @param borderMinute 以这个分钟数区分天
	 * @param borderSecond 以这个秒数区分天
	 * @return
	 */
	public static int calcDiffTimezone(Date time1, Date time2, int borderHour, int borderMinute, int borderSecond){
		Date borderTime1 = DateUtils.changeDateTime(time1, 0, borderHour, borderMinute, borderSecond);
		if(time1.compareTo(borderTime1) < 0 ){
			borderTime1 = DateUtils.changeDateTime(time1, -1, borderHour, borderMinute, borderSecond);
		}
		
		Date borderTime2 =  DateUtils.changeDateTime(time2, 0, borderHour, borderMinute, borderSecond);
		if(time2.compareTo(borderTime2) < 0){
			borderTime2 = DateUtils.changeDateTime(time2, -1, borderHour, borderMinute, borderSecond);
		}
		
		return (int)Math.abs((borderTime2.getTime() - borderTime1.getTime()) / (24 * 3600 * 1000L)) ;
	}
	
	/**
	 * 修改日期
	 * @param theDate 待修改的日期
	 * @param addDays 加减的天数
	 * @param hour 设置的小时
	 * @param minute 设置的分
	 * @param second 设置的秒
	 * @return 修改后的日期
	 */
	public static Date changeDateTime(Date theDate, int addDays, int hour, int minute, int second) {
		if (theDate == null) {
			return null;
		}

		Calendar cal = Calendar.getInstance();
		cal.setTime(theDate);

		cal.add(Calendar.DAY_OF_MONTH, addDays);
		cal.set(Calendar.MILLISECOND, 0);

		if (hour >= 0 && hour <= 24) {
			cal.set(Calendar.HOUR_OF_DAY, hour);
		}
		if (minute >= 0 && minute <= 60) {
			cal.set(Calendar.MINUTE, minute);
		}
		if (second >= 0 && second <= 60) {
			cal.set(Calendar.SECOND, second);
		}

		return cal.getTime();
	}
	
	/**
	 * 获取明天这个时间的date
	 * @param hour
	 * @param minute
	 * @param second
	 * @return
	 */
	public static Date getNextDateTime(int hour, int minute, int second){
		return  changeDateTime(new Date(), 1, hour, minute, second);
	}
}
