package com.ks.model.achieve;

import java.io.Serializable;

public class AchieveAward  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**成就ID*/
	private int achieveId;
	/**奖励类型*/
	private int type;
	/**关联编号*/
	private int assId;
	/**数量*/
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}
