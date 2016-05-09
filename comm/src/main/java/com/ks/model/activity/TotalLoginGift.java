package com.ks.model.activity;

import java.io.Serializable;

/**
 * 
 * @author living.li
 * @date  2014年4月10日
 */
public class TotalLoginGift implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**ID*/
	private String id;
	/**天数*/
	private int day;	
	/**邮件logo*/
	private String logo;
	/**邮件内容*/
	private String context;
	/**物器类型*/
	private int goodsType;
	/**物品ID*/
	private int assId;
	/**物品数量*/
	private int num;	
	/**物品等级*/
	private int goodsLevel;
	/**邮件标题*/
	private String title;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getDay() {
		return day;
	}
	
	public int getAssId() {
		return assId;
	}
	public void setAssId(int assId) {
		this.assId = assId;
	}
	public void setDay(int day) {
		this.day = day;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public int getGoodsType() {
		return goodsType;
	}
	public void setGoodsType(int goodsType) {
		this.goodsType = goodsType;
	}

	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public int getGoodsLevel() {
		return goodsLevel;
	}
	public void setGoodsLevel(int goodsLevel) {
		this.goodsLevel = goodsLevel;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	
}
