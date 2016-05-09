package com.ks.model.shop;

import java.io.Serializable;

import com.ks.model.Weight;

/**
 * 神秘商店物品
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2016年1月15日
 */
public class MysteryShopItem implements Weight, Serializable {

    private static final long serialVersionUID = -3300860552001796711L;
    
	private int id;
	/**类型: 1 : 金币 2: 竞技场 3: 世界boss 4: 荣誉(排名赛)*/
    private int type;
    /**总价格*/
    private int price;
    /**物品编号*/
    private int goodsId;
    /**物品类型*/
    private int goodsType;
    /**物品等级*/
    private int level;
    /**物品数量*/
    private int num;
    /**权重*/
    private int weight;
    /**总权重*/
	private int totalWeight;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
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
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public int getTotalWeight() {
		return totalWeight;
	}
	public void setTotalWeight(int totalWeight) {
		this.totalWeight = totalWeight;
	}
}
