package com.ks.model.filter;

import java.util.Date;

/**
 * 系统日志查询Filter
 * 
 * @author lipp 2016年1月4日
 */
public class ChatMessaggLogFilter extends Filter {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 服务器ID */
	private String serverId;
	/** 聊天类型 */
	private Integer type;
	/** 开始时间 */
	private Date startTime;
	/** 结束时间 */
	private Date endTime;

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

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getServerId() {
	    return serverId;
    }

	public void setServerId(String serverId) {
	    this.serverId = serverId;
    }
}
