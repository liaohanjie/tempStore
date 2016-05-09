package com.ks.model.user;

import java.io.Serializable;
/**
 * 用户规则
 * @author ks
 */
public class UserRule implements Serializable {

	private static final long serialVersionUID = 1L;
	/**等级*/
	private int level;
	/**体力*/
	private int stamina;
	/**cost*/
	private int cost;
	/**好友上限*/
	private int friendCapacity;
	/**下一级经验*/
	private int nextExp;
	/**战魂仓库数*/
	private int soulBox;
	/**物品仓库数*/
	private int itemBox;
	
	
	public int getSoulBox() {
		return soulBox;
	}
	public void setSoulBox(int soulBox) {
		this.soulBox = soulBox;
	}
	public int getItemBox() {
		return itemBox;
	}
	public void setItemBox(int itemBox) {
		this.itemBox = itemBox;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getStamina() {
		return stamina;
	}
	public void setStamina(int stamina) {
		this.stamina = stamina;
	}
	public int getCost() {
		return cost;
	}
	public void setCost(int cost) {
		this.cost = cost;
	}
	public int getFriendCapacity() {
		return friendCapacity;
	}
	public void setFriendCapacity(int friendCapacity) {
		this.friendCapacity = friendCapacity;
	}
	public int getNextExp() {
		return nextExp;
	}
	public void setNextExp(int nextExp) {
		this.nextExp = nextExp;
	}
	
}
