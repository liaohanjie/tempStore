package com.ks.protocol.vo.soul;

import java.util.List;

import com.ks.protocol.Message;
import com.ks.protocol.vo.user.UserSoulVO;
/**
 * 召唤战魂结果
 * @author ks
 */
public class CallSoulResultVO extends Message{

	private static final long serialVersionUID = 1L;
	/**剩余金钱*/
	private int currency;
	/**剩余友情点*/
	private int friendlyPoint;
	/**召唤的战魂*/
	private List<UserSoulVO> souls;
	
	public int getCurrency() {
		return currency;
	}
	public void setCurrency(int currency) {
		this.currency = currency;
	}
	public int getFriendlyPoint() {
		return friendlyPoint;
	}
	public void setFriendlyPoint(int friendlyPoint) {
		this.friendlyPoint = friendlyPoint;
	}
	public List<UserSoulVO> getSouls() {
		return souls;
	}
	public void setSouls(List<UserSoulVO> souls) {
		this.souls = souls;
	}
	
}
