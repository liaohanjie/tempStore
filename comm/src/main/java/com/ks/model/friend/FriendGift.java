package com.ks.model.friend;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
/**
 * 好友赠品
 * @author ks
 */
public class FriendGift implements Serializable {

	private static final long serialVersionUID = 1L;
	/**编号*/
	private int id;
	/**用户编号*/
	private int userId;
	/**好友编号*/
	private int friendId;
	/**好友名称*/
	private String friendName;
	/**好友想要的东西*/
	private List<Integer> want;
	/**好友战魂编号*/
	private int soulId;
	/**好友战魂等级*/
	private int soulLevel;
	/**好友赠送的东西*/
	private int zone;
	/**赠送时间*/
	private Date createTime;
	/**是否已领取*/
	private boolean receieve;
	
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
	public int getFriendId() {
		return friendId;
	}
	public void setFriendId(int friendId) {
		this.friendId = friendId;
	}
	public String getFriendName() {
		return friendName;
	}
	public void setFriendName(String friendName) {
		this.friendName = friendName;
	}
	public List<Integer> getWant() {
		return want;
	}
	public void setWant(List<Integer> want) {
		this.want = want;
	}
	public int getSoulId() {
		return soulId;
	}
	public void setSoulId(int soulId) {
		this.soulId = soulId;
	}
	public int getSoulLevel() {
		return soulLevel;
	}
	public void setSoulLevel(int soulLevel) {
		this.soulLevel = soulLevel;
	}
	public int getZone() {
		return zone;
	}
	public void setZone(int zone) {
		this.zone = zone;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public boolean isReceieve() {
		return receieve;
	}
	public void setReceieve(boolean receieve) {
		this.receieve = receieve;
	}
}
