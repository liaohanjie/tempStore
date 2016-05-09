package com.ks.schedue;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.Logger;
import com.ks.logger.LoggerFactory;
/**
 *  计划任务执行者
 * @author hanjie.l
 *
 */
public class SchedulingRunner implements Runnable {
	
	private static final Logger logger = LoggerFactory.get(SchedulingRunner.class);	
	
	/**
	 * 日期解析
	 */
	private static final SimpleDateFormat dateFormat  = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
	/**
	 * 服务
	 */
	private final ScheduledExecutorService executor;
	
	/**
	 * 任务
	 */
	private final ScheduledTask delegate;
	
	/**
	 * 任务执行解析对象
	 */
	private CronSequenceGenerator cron;
	
	/**
	 * 任务名
	 */
	private String taskName;
	
	
	
	public SchedulingRunner(ScheduledTask delegate,String taskName, CronSequenceGenerator cron, ScheduledExecutorService executor) {
		this.delegate = delegate;
		this.taskName = taskName;
		this.cron = cron;
		this.executor = executor;
	}
	
	public void schedule() {
		Date nextRunTime = cron.next(new Date());
		if (nextRunTime == null) {
			return;
		}
		long initialDelay = nextRunTime.getTime() - System.currentTimeMillis();
		this.executor.schedule(this, initialDelay, TimeUnit.MILLISECONDS);
		if(logger.isDebugEnabled()){
			
			logger.info(String.format("任务[%s]的下次计划执行时间[%s]", this.taskName, dateFormat.format(nextRunTime)));
		}
	}

	@Override
	public void run() {
		try {
			delegate.runTask();
		} catch (Exception e) {
			logger.error(e);
		}
		schedule();
	}

}
