package com.ks.protocol.vo.swaparena;

import com.ks.protocol.Message;

/**
 * 购买挑战次数结果
 * @author hanjie.l
 *
 */
public class BuyTimesResultVO extends Message{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -211359736735685744L;

	/**
	 * 购买完后可挑战次数
	 */
	private int times;
	
	/**
	 * 购买完后今日已购买次数
	 */
	private int todayBuyCount;
	
	/**
	 * 购买完后剩余魂钻
	 */
	private int currency;

	public int getTimes() {
		return times;
	}

	public void setTimes(int times) {
		this.times = times;
	}

	public int getCurrency() {
		return currency;
	}

	public void setCurrency(int currency) {
		this.currency = currency;
	}

	public int getTodayBuyCount() {
		return todayBuyCount;
	}

	public void setTodayBuyCount(int todayBuyCount) {
		this.todayBuyCount = todayBuyCount;
	}
}
