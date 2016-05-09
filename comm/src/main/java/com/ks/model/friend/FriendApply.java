package com.ks.model.friend;

import java.io.Serializable;
import java.util.Date;
/**
 * 好友请求
 * @author ks
 */
public class FriendApply implements Serializable {

	private static final long serialVersionUID = 1L;
	/**用户编号*/
	private int userId;
	/**请求用户编号*/
	private int applyUserId;
	/**创建时间*/
	private Date createTime;
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getApplyUserId() {
		return applyUserId;
	}
	public void setApplyUserId(int applyUserId) {
		this.applyUserId = applyUserId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
