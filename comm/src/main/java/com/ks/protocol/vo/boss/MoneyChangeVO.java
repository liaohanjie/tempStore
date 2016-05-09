package com.ks.protocol.vo.boss;

import com.ks.protocol.Message;

/**
 * 货币变化VO
 * @author hanjie.l
 *
 */
public class MoneyChangeVO  extends Message{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1645813379286396237L;
	/**
	 * 魂钻
	 */
	private int currency;
	/**
	 * 金币
	 */
	private int gold;
	
	public int getCurrency() {
		return currency;
	}
	public void setCurrency(int currency) {
		this.currency = currency;
	}
	public int getGold() {
		return gold;
	}
	public void setGold(int gold) {
		this.gold = gold;
	}
}        