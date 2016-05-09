package com.ks.timer.job;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.ks.logger.LoggerFactory;
import com.ks.timer.BaseTimer;
import com.ks.timer.TimerController;
/**
 * 工作基类 给定具体时间工作
 * @author ks
 *
 */
public abstract class BaseJob extends BaseTimer {
	private static final Logger logger = LoggerFactory.get(BaseJob.class);
	/**执行字符串*/
	protected String context;
	/**任务时间*/
	protected List<JobTime> times;
	/**时间索引*/
	protected int timeIndex;
	/**任务周*/
	protected List<Integer> weeks;
	/**周索引*/
	protected int weekIndex;
	
	public void initialize(String context){
		this.context = context;
		setUnit(TimeUnit.MILLISECONDS);
		String[] strs = context.split("\\?");
		String timesStr = strs[0];
		String weeksStr = strs[1];
		times = new ArrayList<JobTime>();
		weeks = new ArrayList<Integer>();
		for(String hms : timesStr.split(",")){
			times.add(new JobTime(hms));
		}
		for(String week : weeksStr.split(",")){
			if(!week.equals("*")){
				weeks.add(Integer.parseInt(week));
			}
		}
		calculateIndex();
	}
	private void calculateIndex(){
		Calendar c = Calendar.getInstance();
		if(weeks.size()>0){
			int week = c.get(Calendar.DAY_OF_WEEK);
			week=week==1?7:week-1;
			for(;weekIndex<weeks.size();){//大于
				int i = weeks.get(weekIndex);
				if(week>i){
					weekIndex++;
					if(weekIndex==weeks.size()){
						weekIndex=0;
						int weekIn =  weeks.get(weekIndex);
						weekIn=weekIn==7?1:weekIn+1;
						c.add(Calendar.DAY_OF_MONTH, weekIn-(week-6));
						break;
					}
				}else if(week<i){//小于
					c.add(Calendar.DAY_OF_MONTH, i-week);
					break;
				}else{//等于
					JobTime time = times.get(timeIndex);
					int hour = c.get(Calendar.HOUR_OF_DAY);
					if(hour == time.hour){
						int minute = c.get(Calendar.MINUTE);
						if(minute>=time.minute){
							timeIndex++;
							if(timeIndex == times.size()){
								timeIndex=0;
								weekIndex++;
								if(weekIndex==weeks.size()){
									weekIndex=0;
									int weekIn =  weeks.get(weekIndex);
									weekIn=weekIn==7?1:weekIn+1;
									c.add(Calendar.DAY_OF_MONTH, 7);
									break;
								}
							}
						}else{
							break;
						}
					}else if(hour>time.hour){
						timeIndex++;
						if(timeIndex == times.size()){
							timeIndex=0;
							weekIndex++;
							if(weekIndex==weeks.size()){
								weekIndex=0;
								int weekIn =  weeks.get(weekIndex);
								weekIn=weekIn==7?1:weekIn+1;
								c.add(Calendar.DAY_OF_MONTH, 7);
								break;
							}
						}
					}else{
						break;
					}
				}
			}
		}else{
			boolean flage = true;
			for(;timeIndex<times.size();){//大于
				JobTime time = times.get(timeIndex);
				int hour = c.get(Calendar.HOUR_OF_DAY);
				if(hour <= time.hour){
					int minute = c.get(Calendar.MINUTE);
					if(minute>=time.minute){
						timeIndex++;
						if(timeIndex == times.size()){
							timeIndex=0;
							flage = false;
							c.add(Calendar.DAY_OF_MONTH, 1);
							break;
						}
					}else{
						flage = false;
						break;
					}
				}else{
					timeIndex++;
				}
			}
			if(flage){
				timeIndex = 0;
				c.add(Calendar.DAY_OF_MONTH, 1);
			}
			
		}
		JobTime time = times.get(timeIndex);
		c.set(Calendar.HOUR_OF_DAY, time.hour);
		c.set(Calendar.MINUTE, time.minute);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		Timestamp timestamp = new Timestamp(c.getTimeInMillis());
		logger.info("next run job time--------->"+timestamp);
		this.setInitialDelay(c.getTimeInMillis()-System.currentTimeMillis());
		TimerController.register(this);
	}
	@Override
	public final void run() {
		try{
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			logger.info("job runing------------------->"+timestamp);
			runJob();
			logger.info("job run finish -------------------> time:"+(System.currentTimeMillis()-timestamp.getTime()));
		}catch(Exception e){
			logger.error("",e);
		}		
		finally{
			calculateIndex();
		}
	}
	public abstract void runJob();
	
}
