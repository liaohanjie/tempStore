package com.ks.model.activity;

import java.io.Serializable;

/**
 * 活动礼包
 * 
 * @author zhoujf
 * @date 2015年7月6日
 */
public class ActivityGift implements Serializable{

    private static final long serialVersionUID = -2782720697365854429L;
    
	/**编号*/
	private int id;
	/**活动定义*/
	private int activityDefineId;
	/**活动礼包名称*/
	private String name;
	/**关键码，根据define 自定义*/
	private String key1;
	/**关键码，根据define 自定义*/
	private String key2;
	/**活动礼包描述*/
	private String activityGiftDesc;
	/**礼包内容 json 字符串*/
	private String gift;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getActivityDefineId() {
		return activityDefineId;
	}
	public void setActivityDefineId(int activityDefineId) {
		this.activityDefineId = activityDefineId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getKey1() {
		return key1;
	}
	public void setKey1(String key1) {
		this.key1 = key1;
	}
	public String getKey2() {
		return key2;
	}
	public void setKey2(String key2) {
		this.key2 = key2;
	}
	public String getGift() {
		return gift;
	}
	public void setGift(String gift) {
		this.gift = gift;
	}
	public String getActivityGiftDesc() {
		return activityGiftDesc;
	}
	public void setActivityGiftDesc(String activityGiftDesc) {
		this.activityGiftDesc = activityGiftDesc;
	}
}
