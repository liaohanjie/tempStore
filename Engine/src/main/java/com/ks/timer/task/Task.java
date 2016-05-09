package com.ks.timer.task;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;
/**
 * 定时任务
 * @author ks
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Task {
	/**
	 * 执行类型(1:schedule, 2:scheduleAtFixedRate, 3:scheduleWithFixedDelay)
	 * @return
	 */
	TaskTypeEnum type() default TaskTypeEnum.DEFAULT;
	/**
	 * 开始后多久执行
	 * @return 开始后多久执行
	 */
	long initialDelay();
	/**
	 * 第一次执行后多久循环执行
	 * @return 第一次执行后多久循环执行
	 */
	long period() default 0;
	/**
	 * 时间单位
	 * @return 时间单位
	 */
	TimeUnit unit();
}
