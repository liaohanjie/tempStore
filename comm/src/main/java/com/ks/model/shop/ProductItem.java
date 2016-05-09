package com.ks.model.shop;

import java.io.Serializable;

/**
 * 商品物品项
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2015年7月14日
 */
public class ProductItem implements Serializable {

    private static final long serialVersionUID = 1;
    
    private int id;
    /**商品编号*/
    private int productId;
    /**物品编号*/
    private int goodsId;
    /**物品类型*/
    private int goodsType;
    /**物品数量*/
    private int goodsNum;
    /**物品等级*/
    private int goodsLevel;
    /**概率*/
    private double rate;
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
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
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
}
