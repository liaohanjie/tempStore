package com.ks.model.dungeon;

import java.io.Serializable;
/**
 * 掉落
 * @author ks
 */
public class Drop implements Serializable {

	private static final long serialVersionUID = 1L;	
	
	/**掉落编号*/
	private int dropId;
	/**物品类型*/
	private int type;
	/**物品ID*/
	private int assId;
	/**数量*/
	private int num;
	/**概率*/
	private double rate;

	
	
	public int getDropId() {
		return dropId;
	}
	public void setDropId(int dropId) {
		this.dropId = dropId;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getAssId() {
		return assId;
	}
	public void setAssId(int assId) {
		this.assId = assId;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	
}
