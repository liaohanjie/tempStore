package com.ks.model;

import java.io.Serializable;

/**
 * 爬塔试炼奖励配置
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2015年11月13日
 */
public class Award implements Serializable {

    
    private static final long serialVersionUID = -8892611209908692637L;
    
    /**奖励编号*/
	private int id;
	/**物品编号*/
	private int goodsId;
	/**物品类型*/
	private int goodsType;
	/**数量*/
	private int num;
	/**等级*/
	private int level;
	/**概率(1必出, 小于1的概率*/
    private double rate;
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(int goodsId) {
		this.goodsId = goodsId;
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
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
}
