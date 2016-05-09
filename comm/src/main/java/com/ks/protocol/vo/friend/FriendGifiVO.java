package com.ks.protocol.vo.friend;

import java.util.List;

import com.ks.model.friend.FriendGift;
import com.ks.protocol.Message;
/**
 * 好友礼物
 * @author ks
 */
public class FriendGifiVO extends Message {

	private static final long serialVersionUID = 1L;
	/**编号*/
	private int id;
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
	private long createTime;
	/**是否已领取*/
	private boolean receieve;
	
	public void init(FriendGift o){
		this.id = o.getId();
		this.friendId = o.getFriendId();
		this.friendName = o.getFriendName();
		this.want = o.getWant();
		this.soulId = o.getSoulId();
		this.soulLevel = o.getSoulLevel();
		this.zone = o.getZone();
		this.createTime = o.getCreateTime().getTime();
		this.receieve = o.isReceieve();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public boolean isReceieve() {
		return receieve;
	}

	public void setReceieve(boolean receieve) {
		this.receieve = receieve;
	}
}
