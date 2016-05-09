package com.ks.model.user;

import java.io.Serializable;
import java.util.Date;
/**
 * 用户BUff
 * @author living.li
 * @date   2014年6月24日
 */
public class UserBuff  implements Serializable {

	public static final int TIME_ONE_HOUR=60*60*1000;
	public static final int TIME_ONE_DAY=24*60*60*1000;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final int BUFF_ID_副本钥匙=1;
	
	public static final int BUFF_ID_赠送vip=2;
	
	public static final int BUFF_ID_黄金月卡 = 15;
	
	public static final int BUFF_ID_钻石月卡 = 16;

	private int userId;
	
	private int buffId;
	
	private int value;
	
	private Date endTime;
	
	private Date createTime;
	
	private Date updateTime;

	
	public static UserBuff create(int userId,int buffId,int assValue,Date endTime){
		UserBuff buff=new UserBuff();
		buff.setUserId(userId);
		buff.setBuffId(buffId);
		buff.setValue(assValue);
		buff.setEndTime(endTime);
		return buff;
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

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	
	
	
	
}
