package com.ks.protocol.vo.shop;

import com.ks.model.shop.MysteryShopItem;
import com.ks.protocol.Message;

/**
 * 神秘商店
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2016年1月15日
 */
public  class  MysteryShopItemVO extends Message {

    private static final long serialVersionUID = 513845424144989237L;
    
    private int id;
	/**类型: 1 : 金币 2: 竞技场 3: 世界boss 4: 荣誉'*/
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
    
    public void init(MysteryShopItem o) {
    	this.id = o.getId();
    	this.type = o.getType();
    	this.price = o.getPrice();
    	this.goodsId = o.getGoodsId();
    	this.goodsType = o.getGoodsType();
    	this.level = o.getLevel();
    	this.num = o.getNum();
    }
    
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
}