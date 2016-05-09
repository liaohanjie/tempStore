package com.ks.model.activity;

import java.io.Serializable;
import java.util.Date;
/**
 * 召唤出指定战魂的公告
 * 
 * @author fengpeng E-mail:fengpeng_15@163.com
 *
 * @version 创建时间：2014年10月21日 下午3:06:00
 */
public class CallSoulNotice implements Serializable {

	private static final long serialVersionUID = 1L;
	

	/**编号*/
	private int id;
	/**活动Id*/
	private int defineId;
	/**标题*/
	private String title;
	/**内容*/
	private String context;
	/**创建时间*/
	private Date startTime;
	/**修改时间*/
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
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}


	public int getDefineId() {
		return defineId;
	}


	public void setDefineId(int defineId) {
		this.defineId = defineId;
	}

	
}
