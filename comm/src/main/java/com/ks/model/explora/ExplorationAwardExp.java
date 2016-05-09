package com.ks.model.explora;

import java.io.Serializable;

/**
 * @author fengpeng E-mail:fengpeng_15@163.com
 * @version 创建时间：2014年12月29日 下午2:39:00
 * 类说明
 */
public class ExplorationAwardExp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**战魂稀有度*/
	private int soulRare;
	/**战魂探索时间*/
	private int hourTime;
	/**奖励经验*/
	private int exp;
	/**奖励金币*/
	private int gold;
	/**奖励最少物品个数*/
	private int awardNum1;
	/**奖励物品个数的概率*/
	private double rate1;
	/**奖励物品最多个数*/
	private int awardNum2;
	public int getAwardNum1() {
		return awardNum1;
	}
	public void setAwardNum1(int awardNum1) {
		this.awardNum1 = awardNum1;
	}
	public double getRate1() {
		return rate1;
	}
	public void setRate1(double rate1) {
		this.rate1 = rate1;
	}
	public int getAwardNum2() {
		return awardNum2;
	}
	public void setAwardNum2(int awardNum2) {
		this.awardNum2 = awardNum2;
	}
	public double getRate2() {
		return rate2;
	}
	public void setRate2(double rate2) {
		this.rate2 = rate2;
	}
	/**奖励物品个数的概率*/
	private double rate2;
	public int getSoulRare() {
		return soulRare;
	}
	public void setSoulRare(int soulRare) {
		this.soulRare = soulRare;
	}
	public int getHourTime() {
		return hourTime;
	}
	public void setHourTime(int hourTime) {
		this.hourTime = hourTime;
	}
	public int getExp() {
		return exp;
	}
	public void setExp(int exp) {
		this.exp = exp;
	}
	public int getGold() {
		return gold;
	}
	public void setGold(int gold) {
		this.gold = gold;
	}
	

}
