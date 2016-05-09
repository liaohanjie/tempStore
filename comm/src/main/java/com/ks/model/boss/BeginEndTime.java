package com.ks.model.boss;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 开启时间对象
 * @author hanjie.l
 *
 */
public class BeginEndTime {
	
	
	/**
	 * 开启
	 */
	private Calendar begin;
	
	/**
	 * 结束
	 */
	private Calendar end;
	
	public BeginEndTime(String s) throws ParseException {
		String[] beginEndSting = s.split("-");
		String begin = beginEndSting[0];
		String end = beginEndSting[1];
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
		
		Date beginparse = dateFormat.parse(begin);
		this.begin = Calendar.getInstance();
		this.begin.setTime(beginparse);
		
		Date endparse = dateFormat.parse(end);
		this.end = Calendar.getInstance();
		this.end.setTime(endparse);
	}

	public Calendar getBegin() {
		return begin;
	}

	public void setBegin(Calendar begin) {
		this.begin = begin;
	}

	public Calendar getEnd() {
		return end;
	}

	public void setEnd(Calendar end) {
		this.end = end;
	}
}
