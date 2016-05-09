package com.ks.protocol.vo.activity;

import com.ks.protocol.Message;

public  class  LoginGiftVO extends Message  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int day;	

	/**物器类型*/
	private int goodsType;
	/**物品ID*/
	private int assId;
	/**物品数量*/
	private int goodsNum;	
	/**物品等级*/
	private int goodsLevel;
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	public int getGoodsType() {
		return goodsType;
	}
	public void setGoodsType(int goodsType) {
		this.goodsType = goodsType;
	}

	public int getAssId() {
		return assId;
	}
	public void setAssId(int assId) {
		this.assId = assId;
	}
	public int getGoodsNum() {
		return goodsNum;
	}
	public void setGoodsNum(int goodsNum) {
		this.goodsNum = goodsNum;
	}
	public int getGoodsLevel() {
		return goodsLevel;
	}
	public void setGoodsLevel(int goodsLevel) {
		this.goodsLevel = goodsLevel;
	}
	
	public void init(int day,int goodsType,int assId,int goodsNum,int goodsLevel){
		this.day = day;
		this.goodsType = goodsType;
		this.assId = assId;
		this.goodsNum = goodsNum;
		this.goodsLevel =goodsLevel;
		
	}

	
}
