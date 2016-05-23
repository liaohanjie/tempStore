package com.living.util;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * 日期处理
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2015年10月29日
 */
public class DateUtil {

	public static String dateTostring(Date date, String format) {
		if (date == null || format == null || format.trim().equals("")) {
			return null;
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}
}
