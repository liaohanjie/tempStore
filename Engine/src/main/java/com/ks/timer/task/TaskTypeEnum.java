package com.ks.timer.task;

public enum TaskTypeEnum {
	/**旧方式*/
	DEFAULT,
	/**只执行一次,参数(initialDelay,unit)*/
	SCHEDULE,
	/**固定时间频率执行,参数(initialDelay,period,unit)*/
	SCHEDULE_AT_FIXED_RATE,
	/**固定延迟频率执行,参数(initialDelay,period,unit)*/
	SCHEDULE_WITH_FIXED_DELAY
}
