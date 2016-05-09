package com.ks.protocol.vo.soul;

import com.ks.model.user.UserSoulMap;
import com.ks.protocol.Message;

/**
 * 
 * @author living.li
 * @date  2014年4月24日 
 * 用户图鉴 
 * 
 */
public class UserSoulMapVO extends Message  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**战魂ID**/
	private int soulId;
	/**状态**/
	private int state;
	
	public void init(UserSoulMap o){
		this.soulId = o.getSoulId();
		this.state = o.getState();
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}
