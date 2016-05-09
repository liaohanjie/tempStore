package com.ks.protocol.vo.user;

import com.ks.model.user.UserBuff;
import com.ks.protocol.Message;

public class UserBuffVO extends Message{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**用户ID*/
	private int userId;
	/**buffID*/
	private int buffId;
	/**关联值*/
	private int value;
	/**结束时间*/
	private long endTime;
	
	public void init(UserBuff o){
		this.userId = o.getUserId();
		this.buffId = o.getBuffId();
		this.value = o.getValue();
		this.endTime = o.getEndTime().getTime();
	}

	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getBuffId() {
		return buffId;
	}
	public void setBuffId(int buffId) {
		this.buffId = buffId;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}


	public long getEndTime() {
		return endTime;
	}


	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}
	
	
	
}
