package com.ks.model.filter;

import java.util.Date;

/**
 * 游戏日志查询Filter
 * @author lipp
 * 2014年11月3日
 */
public class GameLoggerFilter  extends Filter{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 用户编号 */
	private Integer userId;
	/** 日志类型 */
	private int loggerType;
	/** 类型 */
	private Integer type;
	/** 开始时间 */
	private Date startTime;
	/** 结束时间 */
	private Date endTime;
	
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public int getLoggerType() {
		return loggerType;
	}
	public void setLoggerType(int loggerType) {
		this.loggerType = loggerType;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

}
