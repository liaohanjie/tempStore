package com.ks.timer.task;

import org.apache.log4j.Logger;

import com.ks.logger.LoggerFactory;
import com.ks.timer.BaseTimer;
/**
 * 定时任务基类
 * @author ks
 *
 */
public abstract class BaseTask extends BaseTimer {
	
	private static Logger logger = LoggerFactory.get(BaseTask.class);
	
	public abstract void runTask();

	@Override
	public final void run() {
		long startTime = System.currentTimeMillis();
		logger.info("task run --------> ");
		try{
			runTask();
		}catch (Exception e) {
			logger.error("",e);
		}
		
		logger.info("task run finish ------------> time:"+(System.currentTimeMillis()-startTime));
	}
	
	
}
