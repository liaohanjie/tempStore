package com.ks.model.activity;

import java.io.Serializable;
import java.util.Date;

/**
 * 活动礼包领取记录
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2015年7月13日
 */
public class ActivityGiftRecord implements Serializable{

    private static final long serialVersionUID = 1;
    
	/**编号*/
	private int id;
	/**用户编号*/
	private int userId;
	/**活动礼包ID*/
	private int activityGiftId;
	/**创建时间*/
	private Date createTime;
	
	public ActivityGiftRecord() {
    }
	
	public ActivityGiftRecord(int id, int userId, int activityGiftId, Date createTime) {
	    this.id = id;
	    this.userId = userId;
	    this.activityGiftId = activityGiftId;
	    this.createTime = createTime;
    }
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getActivityGiftId() {
		return activityGiftId;
	}
	public void setActivityGiftId(int activityGiftId) {
		this.activityGiftId = activityGiftId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
