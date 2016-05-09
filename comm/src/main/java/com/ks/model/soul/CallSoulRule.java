package com.ks.model.soul;

import java.io.Serializable;
/**
 * 召唤战魂规则
 * @author ks
 */
public class CallSoulRule implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**友情召唤*/
	public static final int TYPE_FRIENDLY_POINT = 1;
	/**上位召唤*/
	public static final int TYPE_CURRENCY = 2;	
	
	public int id;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	/**类型*/
	private int type;
	/**战魂编号*/
	private int soulId;
	/**等级*/
	private int level;
	/**概率*/
	private int rate;
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getSoulId() {
		return soulId;
	}
	public void setSoulId(int soulId) {
		this.soulId = soulId;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getRate() {
		return rate;
	}
	public void setRate(int rate) {
		this.rate = rate;
	}
	
}
