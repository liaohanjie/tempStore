package com.ks.schedue;

import java.util.TimeZone;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import org.apache.log4j.Logger;
import com.ks.logger.LoggerFactory;

/**
 * 定时任务调度器
 * @author hanjie.l
 *
 */
public class SimpleScheduler {
	
	private static final Logger logger = LoggerFactory.get(SimpleScheduler.class);	

	private static ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(4);
	
	
	/**
	 * 启动一个任务
	 * @param task
	 * @param cron
	 */
	public static void schedule(ScheduledTask task, String taskName, String cron) {
		try {
			CronSequenceGenerator cronExpression = new CronSequenceGenerator(cron, TimeZone.getDefault());
			new SchedulingRunner(task, taskName, cronExpression, scheduledExecutorService).schedule();
		} catch (Exception ex) {
			logger.error("执行器不接受[" + taskName + "]该任务", ex);
		}
	}
	
	/**
	 * 启动一个Debug任务
	 * @param task
	 * @param cron
	 */
	public static void debugSchedule(ScheduledTask task, String taskName, String cron) {
		try {
			CronSequenceGenerator cronExpression = new CronSequenceGenerator(cron, TimeZone.getDefault());
			new DeBugSchedulingRunner(task, taskName, cronExpression, scheduledExecutorService).schedule();
		} catch (Exception ex) {
			logger.error("执行器不接受[" + taskName + "]该任务");
		}
	}
}
