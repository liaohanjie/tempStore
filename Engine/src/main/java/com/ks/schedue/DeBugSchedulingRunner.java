package com.ks.schedue;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.ks.logger.LoggerFactory;
/**
 * debug任务执行者
 * @author hanjie.l
 *
 */
public class DeBugSchedulingRunner implements Runnable{
	
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
	
	/**
	 * 下次执行时间
	 */
	private long nextExecuteTime;
	
	
	
	public DeBugSchedulingRunner(ScheduledTask delegate,String taskName, CronSequenceGenerator cron, ScheduledExecutorService executor) {
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
		this.nextExecuteTime = nextRunTime.getTime();
		long initialDelay = nextRunTime.getTime() - System.currentTimeMillis();
		if(initialDelay > 60*1000){
			this.executor.schedule(this, 30*1000L, TimeUnit.MILLISECONDS);
		}else{
			this.executor.schedule(this, initialDelay, TimeUnit.MILLISECONDS);
			if(logger.isDebugEnabled()){
				logger.info(String.format("任务[%s]的下次计划执行时间[%s]", this.taskName, dateFormat.format(nextRunTime)));
			}
		}
	}

	@Override
	public void run() {
		try {
			if(System.currentTimeMillis() > nextExecuteTime){
				delegate.runTask();
			}
		} catch (Exception e) {
			logger.error(e);
		}
		schedule();
	}
}
