package com.ks.model.friend;

import java.io.Serializable;
/**
 * 好友赠品规则
 * @author ks
 */
public class FriendGifiRule implements Serializable {

	private static final long serialVersionUID = 1L;
	/**编号*/
	private int zone;
	/**类型*/
	private int type;
	/**礼品编号*/
	private int gifiId;
	/**数量*/
	private int num;
	public int getZone() {
		return zone;
	}
	public void setZone(int zone) {
		this.zone = zone;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getGifiId() {
		return gifiId;
	}
	public void setGifiId(int gifiId) {
		this.gifiId = gifiId;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	
}
