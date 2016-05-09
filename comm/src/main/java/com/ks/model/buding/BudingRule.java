package com.ks.model.buding;

import java.io.Serializable;
/**
 * 建筑规则
 * @author ks
 */
public class BudingRule implements Serializable {

	private static final long serialVersionUID = 1L;
	/**建筑编号*/
	private int budingId;
	/**等级*/
	private int level;
	/**所需金钱*/
	private int gold;
	/**冷却时间*/
	private int time;
	/**收集次数*/
	private int count;
	public int getBudingId() {
		return budingId;
	}
	public void setBudingId(int budingId) {
		this.budingId = budingId;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getGold() {
		return gold;
	}
	public void setGold(int gold) {
		this.gold = gold;
	}
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
}
