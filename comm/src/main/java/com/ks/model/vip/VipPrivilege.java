package com.ks.model.vip;

import java.io.Serializable;

/**
 * @author fengpeng E-mail:fengpeng_15@163.com
 * 
 * @version 创建时间：2014年7月25日 下午3:48:01
 * 
 * vip特权
 */
public class VipPrivilege implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static int PROPERTY_扫荡=1;
	public static int PROPERTY_成长计划=2;
	public static int PROPERTY_团队狩猎=4;
	public static int PROPERTY_每周首次登陆时赠送铜钥匙1枚=8;
	public static int PROPERTY_每周首次登陆时赠送银钥匙1枚=16;
	public static int PROPERTY_每周首次登陆时赠送金钥匙1枚=32;
	public static int PROPERTY_神秘商店的永久开放=64;
	/**vip等级*/
	private int vipGrade;
	/**累计充值数*/
	private int totalCurrency;
	/**每天赠送的免费的扫荡次数*/
	private int everydaySweepCount;
	/**每天额外购买扫荡次数*/
	private int buySweepCount;
	/**体力上限增加值*/
	private int addStamina;
	/**解锁vip神秘商店物品等级*/
	private int storeItemGrade;
	/**vip功能*/
	private int property;
	/**增加购买体力次数*/
	private int addBuyStaminaCount;
	/**额外强化经验*/
	private int extraStrengthenExp;
	/**减少探索时间, 百分比*/
	private double reduceExploreTime;
	/**额外战魂容量*/
	private int extraSoulCapacity;
	/**点金手使用次数*/
	private int coinHandNum;
	
	public int getAddBuyStaminaCount() {
		return addBuyStaminaCount;
	}
	public void setAddBuyStaminaCount(int addBuyStaminaCount) {
		this.addBuyStaminaCount = addBuyStaminaCount;
	}
	public int getVipGrade() {
		return vipGrade;
	}
	public void setVipGrade(int vipGrade) {
		this.vipGrade = vipGrade;
	}
	public int getTotalCurrency() {
		return totalCurrency;
	}
	public void setTotalCurrency(int totalCurrency) {
		this.totalCurrency = totalCurrency;
	}
	public int getEverydaySweepCount() {
		return everydaySweepCount;
	}
	public void setEverydaySweepCount(int everydaySweepCount) {
		this.everydaySweepCount = everydaySweepCount;
	}
	public int getBuySweepCount() {
		return buySweepCount;
	}
	public void setBuySweepCount(int buySweepCount) {
		this.buySweepCount = buySweepCount;
	}
	public int getAddStamina() {
		return addStamina;
	}
	public void setAddStamina(int addStamina) {
		this.addStamina = addStamina;
	}
	public int getStoreItemGrade() {
		return storeItemGrade;
	}
	public void setStoreItemGrade(int storeItemGrade) {
		this.storeItemGrade = storeItemGrade;
	}
	public int getProperty() {
		return property;
	}
	public void setProperty(int property) {
		this.property = property;
	}
	public int getExtraStrengthenExp() {
		return extraStrengthenExp;
	}
	public void setExtraStrengthenExp(int extraStrengthenExp) {
		this.extraStrengthenExp = extraStrengthenExp;
	}
	public double getReduceExploreTime() {
		return reduceExploreTime;
	}
	public void setReduceExploreTime(double reduceExploreTime) {
		this.reduceExploreTime = reduceExploreTime;
	}
	public int getExtraSoulCapacity() {
		return extraSoulCapacity;
	}
	public void setExtraSoulCapacity(int extraSoulCapacity) {
		this.extraSoulCapacity = extraSoulCapacity;
	}
	public int getCoinHandNum() {
		return coinHandNum;
	}
	public void setCoinHandNum(int coinHandNum) {
		this.coinHandNum = coinHandNum;
	}
}
