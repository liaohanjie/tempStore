package com.ks.model.soul;

import java.io.Serializable;
/**
 * 活动指定召唤战魂
 * 
 * @author fengpeng E-mail:fengpeng_15@163.com
 *
 * @version 创建时间：2014年10月15日 上午11:24:59
 */
public class ActivitySoulRule implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**友情召唤*/
	public static final int TYPE_FRIENDLY_POINT = 1;
	/**上位召唤*/
	public static final int TYPE_CURRENCY = 2;	
	/**活动召唤战魂*/
	public static final int TYPE_ACTIVITY = 3;
	private int id;
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
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
}
