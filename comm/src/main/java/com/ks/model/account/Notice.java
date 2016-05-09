package com.ks.model.account;

import java.io.Serializable;
import java.util.Date;

/**
 * 公告
 * 
 * @author living.li
 * @date 2014年5月29日
 */
public class Notice implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int id;

	private Date startTime;

	private Date endTime;

	private String context;

	private String serverNo;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getServerNo() {
		return serverNo;
	}

	public void setServerNo(String serverNo) {
		this.serverNo = serverNo;
	}

	@Override
	public String toString() {
		return "Notice [id=" + id + ", startTime=" + startTime + ", endTime=" + endTime + ", context=" + context + ", serverNo=" + serverNo + "]";
	}

}
