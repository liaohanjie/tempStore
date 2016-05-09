package com.ks.model.achieve;

import java.io.Serializable;

/**
 * 成就规则
 * @author living.li
 * @date  2014年4月25日
 */
public class Achieve implements Serializable{

	public static final int TYPE_好友数量 = 1;
	public static final int TYPE_游戏币数量 = 2;
	public static final int TYPE_用户等级 = 3;
	public static final int TYPE_关卡 = 4;
	public static final int TYPE_收集战魂 = 5;
	//public static final int TYPE_怪物击杀 = 6;
	public static final int TYPE_购买宝石 = 7;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**成就ID*/
	private int achieveId;
	/**成就类型**/
	private int type;
	/**关联ID*/
	private int assId;
	/**目标数量*/
	private int num;
	
	public int getAchieveId() {
		return achieveId;
	}
	public void setAchieveId(int achieveId) {
		this.achieveId = achieveId;
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
	
	
}
