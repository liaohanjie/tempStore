package com.ks.timer;

import java.util.concurrent.TimeUnit;

import com.ks.timer.task.TaskTypeEnum;

/**
 * 定时器基类
 * @author ks
 *
 */
public abstract class BaseTimer implements Runnable {
	/**定时任务类型*/
	protected TaskTypeEnum type;
	/**注册后多久执行*/
	protected long initialDelay;
	/**第一次执行完毕后每隔多长时间执行一次，为0时不循环执行*/
	protected long period;
	/**时间单位*/
	protected TimeUnit unit;
	
	public void setInitialDelay(long initialDelay) {
		this.initialDelay = initialDelay;
	}
	public void setPeriod(long period) {
		this.period = period;
	}
	public void setUnit(TimeUnit unit) {
		this.unit = unit;
	}
	public TaskTypeEnum getType() {
		return type;
	}
	public void setType(TaskTypeEnum type) {
		this.type = type;
	}
}
