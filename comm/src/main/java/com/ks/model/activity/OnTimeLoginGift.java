package com.ks.model.activity;

import java.io.Serializable;
import java.util.Date;
/**
 * 指定时间段登录奖励
 * @author living.li
 * @date  2014年4月9日
 */
public class OnTimeLoginGift implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**ID*/
	private String id;
	
	private int day;
	/**开始时间*/
	private Date startTime;
	/**结束时间*/
	private Date endTime;
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
	/**标题*/
	private String title;
	
	public int getGoodsLevel(){
		return goodsLevel;
	}
	public void setGoodsLevel(int goodsLevel) {
		this.goodsLevel = goodsLevel;
	}
	public String getId() {
		return id;
	}
	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContext() {
		return context;
	}

	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
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

	public int getAssId() {
		return assId;
	}

	public void setAssId(int assId) {
		this.assId = assId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	

}
