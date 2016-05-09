package com.ks.protocol.vo.buding;

import com.ks.model.buding.UserBuding;
import com.ks.protocol.Message;
import com.ks.protocol.MessageFactory;

public class LevelUpBudingResultVO extends Message {

	private static final long serialVersionUID = 1L;
	/**当前金钱*/
	private int gold;
	/**升级后的建筑*/
	private UserBudingVO userBuding;
	
	public void init(int gold , UserBuding userBuding){
		this.gold = gold;
		this.userBuding = MessageFactory.getMessage(UserBudingVO.class);
		this.userBuding.init(userBuding);
	}
	
	public int getGold() {
		return gold;
	}
	public void setGold(int gold) {
		this.gold = gold;
	}
	public UserBudingVO getUserBuding() {
		return userBuding;
	}
	public void setUserBuding(UserBudingVO userBuding) {
		this.userBuding = userBuding;
	}
	
	
}
