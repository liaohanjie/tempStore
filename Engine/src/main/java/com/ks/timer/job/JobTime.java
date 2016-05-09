package com.ks.timer.job;

import com.ks.timer.TimerException;

/**
 * 任务时间
 * @author ks
 *
 */
class JobTime {
	/**小时*/
	protected int hour;
	/**分钟*/
	protected int minute;
	
	public JobTime(String hms) {
		String[] hm  = hms.split(":");
		hour=Integer.parseInt(hm[0]);
		minute=Integer.parseInt(hm[1]);
		if(hour>23){
			throw new TimerException("hours must be less than 23");
		}
		if(minute>59){
			throw new TimerException("minutes must be less than 59");
		}
	}
}
