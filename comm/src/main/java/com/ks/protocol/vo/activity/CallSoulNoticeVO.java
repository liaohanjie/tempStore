package com.ks.protocol.vo.activity;

import com.ks.model.activity.CallSoulNotice;
import com.ks.protocol.Message;
/**
 * 召唤出指定战魂的公告
 * 
 * @author fengpeng E-mail:fengpeng_15@163.com
 *
 * @version 创建时间：2014年10月21日 下午3:06:00
 */
public class CallSoulNoticeVO extends Message {

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
	private long startTime;
	/**修改时间*/
	private long endTime;
	
	private int soulId;

	
	public void init(CallSoulNotice o,int soulId){
		this.defineId = o.getDefineId();
		this.context=o.getContext();
		this.title=o.getTitle();
		this.id=o.getId();
		this.soulId=soulId;		
		this.startTime = o.getStartTime().getTime();
		this.endTime = o.getEndTime().getTime();

	}
	public long getStartTime() {
		return startTime;
	}


	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}


	public long getEndTime() {
		return endTime;
	}


	public void setEndTime(long endTime) {
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


	public int getSoulId() {
		return soulId;
	}


	public void setSoulId(int soulId) {
		this.soulId = soulId;
	}

	
}
