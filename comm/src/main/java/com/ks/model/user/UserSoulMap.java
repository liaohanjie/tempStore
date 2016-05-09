package com.ks.model.user;


/**
 * 图鉴
 * 
 * @author living.li
 * @date 2014年4月24日
 */
public class UserSoulMap {

	public static final String NOT_IN_MAP="1010500";
	public static final int STATE_见过 = 1;
	public static final int STATE_拥有过 = 2;
	
	private int userId;
	
	private int soulId;
	
	private int state;

	
	public static UserSoulMap create(int userId,int soulId,int state){
		UserSoulMap map=new UserSoulMap();
		map.setUserId(userId);
		map.setSoulId(soulId);
		map.setState(state);
		return map;
	}
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getSoulId() {
		return soulId;
	}

	public void setSoulId(int soulId) {
		this.soulId = soulId;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

}
