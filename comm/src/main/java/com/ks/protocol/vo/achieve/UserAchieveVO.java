/**
 * 
 */
package com.ks.protocol.vo.achieve;

import com.ks.model.achieve.UserAchieve;
import com.ks.protocol.Message;

/**
 * @author living.li
 * @date  2014年4月26日
 */
public class UserAchieveVO extends Message{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int STATE_已达成=2;
	public static final int STATE_已领取=3;

	/**用户成就*/
	private int achieveId;
	/** 当前完成数量 */
	private int currentNum;
	/**状态*/
	private int state;
	
	public void init(UserAchieve o){
		this.achieveId = o.getAchieveId();
		this.currentNum=o.getCurrentNum();
		this.state = o.getState();
	}


	public int getAchieveId() {
		return achieveId;
	}

	public void setAchieveId(int achieveId) {
		this.achieveId = achieveId;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}


	public int getCurrentNum() {
		return currentNum;
	}


	public void setCurrentNum(int currentNum) {
		this.currentNum = currentNum;
	}
	
	
}
