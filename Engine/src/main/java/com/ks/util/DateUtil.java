package com.ks.util;

import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具
 * @author ks
 */
public class DateUtil {
	
	/**
	 * 是否在今天之前
	 * @param date 要比较的时间
	 * @return true 为今天之前
	 */
	public static boolean isBeforeToDay(Date date){
		Date now = getZeroTime(new Date());
		date = getZeroTime(date);
		return date.getTime()<now.getTime();
	}
	
	/**
	 * 获得一个日期0点的时间
	 * @param date 日期
	 * @return 0点日期
	 */
	public static Date getZeroTime(Date date){
		Calendar caldate = Calendar.getInstance();
		caldate.setTime(date);
		caldate.set(Calendar.HOUR_OF_DAY, 0);
		caldate.set(Calendar.MINUTE,0);
		caldate.set(Calendar.SECOND, 0);
		caldate.set(Calendar.MILLISECOND, 0);
		return caldate.getTime();
	}
	/**
	 * 获得昨天0点
	 * @return
	 */
	public static Date getYesterdayZero(){
		Calendar caldate = Calendar.getInstance();		
		caldate.add(Calendar.DAY_OF_YEAR, -1);
		caldate.set(Calendar.HOUR_OF_DAY, 0);
		caldate.set(Calendar.MINUTE,0);
		caldate.set(Calendar.SECOND, 0);
		caldate.set(Calendar.MILLISECOND, 0);
		return caldate.getTime();
	}
	/**
	 * 获得今天某个小时的时间点
	 * @param hour 小时
	 * @return 今天某个小时时间点
	 */
	public static Date getTodayHourTime(int hour){
		Calendar caldate = Calendar.getInstance();		
		caldate.set(Calendar.HOUR_OF_DAY, hour);
		caldate.set(Calendar.MINUTE,0);
		caldate.set(Calendar.SECOND, 0);
		caldate.set(Calendar.MILLISECOND, 0);
		return caldate.getTime();
	}
	public static Calendar getNow(){
		return Calendar.getInstance();
	}
	/**
	 * 今日0点0分0秒
	 * @return
	 */
	public static Calendar getTodayZero(){
		Calendar c=getNow();
		c.set(Calendar.HOUR_OF_DAY,0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c;
	}
	/**
	 * 今日23点59分59秒
	 * @return
	 */
	public static Calendar getTodayLastSecond(){
		Calendar c=getNow();
		c.set(Calendar.HOUR_OF_DAY,23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		c.set(Calendar.MILLISECOND, 999);
		return c;
	}
	
	/**
	 * 23点59分59秒
	 * @return
	 */
	public static Calendar getDateLastSec(int day){
		Calendar c=getNow();
		c.add(Calendar.DAY_OF_YEAR, day);
		c.set(Calendar.HOUR_OF_DAY,23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		c.set(Calendar.MILLISECOND, 999);
		return c;
	}
	/**
	 * 昨日0点0分0秒
	 * @return
	 */
	public static Calendar getYestoDayZero(){
		Calendar c=getNow();
		c.set(Calendar.HOUR_OF_DAY,0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c;
	}
	
	/**
	 * 昨日0点0分0秒
	 * @return
	 */
	public static Calendar getDayZero(int day){
		Calendar c=getNow();
		c.add(Calendar.DAY_OF_YEAR, day);
		c.set(Calendar.HOUR_OF_DAY,0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c;
	}
	
	/**
	 * 得天某天的0点
	 * @return
	 */
	public static Calendar getZero(Date date){
		Calendar c=getNow();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY,0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c;
	}
	/**
	 * 中国地区使用（同第一天为周一）
	 * @return
	 */
	public static Calendar weekFirstZero(Date date){
		Calendar c=getNow();
		c.setTime(date);
		c.set(Calendar.DAY_OF_WEEK,2);
		c.set(Calendar.HOUR_OF_DAY,0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c;
	}
	
	/**
	 * 中国地区使用（同第一天为周一）
	 * @return
	 */
	public static Calendar monthFristZero(Date date){
		Calendar c=getNow();
		c.setTime(date);
		c.set(Calendar.DAY_OF_MONTH,1);
		c.set(Calendar.HOUR_OF_DAY,0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c;
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
