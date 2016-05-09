package com.ks.model.friend;

import java.io.Serializable;
import java.util.Date;
/**
 * 好友
 * @author ks
 */
public class Friend implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**机器人战魂id*/
	public static final int[][] ROBOT_SOULS=new int[][]{
		{1010001,1010006,1010011,1010016,1010021,1010026},
		{1010001,1010006,1010011,1010016,1010021,1010026},
		{1010002,1010007,1010012,1010017,1010022,1010027},
		{1010003,1010008,1010013,1010018,1010023,1010028},
		{1010004,1010009,1010014,1010019,1010024,1010029}
		};
	
	public static final int[] ROBOT_SOUL_LEVEL=new int[]{-2,-1,0,1,2,3};
	
	/**珍藏状态*/
	public static final int STATUS_COLLECTION = 0b1;
	
	/**用户编号*/
	private int userId;
	/**好友编号*/
	private int friendId;
	/**状态*/
	private int status;
	/**创建时间*/
	private Date createTime;
	/**修改时间*/
	private Date updateTime;
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getFriendId() {
		return friendId;
	}
	public void setFriendId(int friendId) {
		this.friendId = friendId;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
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
