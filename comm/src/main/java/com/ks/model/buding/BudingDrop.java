package com.ks.model.buding;

import java.io.Serializable;
/**
 * 建筑掉落
 * @author ks
 */
public class BudingDrop implements Serializable {

	private static final long serialVersionUID = 1L;
	/**编号*/
	private int id;
	/**建筑编号*/
	private int budingId;
	/**等级*/
	private int level;
	/**材料编号*/
	private int stuffId;
	/**数量*/
	private int num;
	/**概率*/
	private int rate;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
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
	public int getStuffId() {
		return stuffId;
	}
	public void setStuffId(int stuffId) {
		this.stuffId = stuffId;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public int getRate() {
		return rate;
	}
	public void setRate(int rate) {
		this.rate = rate;
	}
	
}
