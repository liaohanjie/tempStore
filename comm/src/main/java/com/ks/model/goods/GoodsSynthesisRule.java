package com.ks.model.goods;

import java.io.Serializable;
/**
 * 
 * @author ks
 */
public class GoodsSynthesisRule implements Serializable {

	private static final long serialVersionUID = 1L;
	/**编号*/
	private int id;
	/**物品类型*/
	private int goodsType;
	/**物品编号*/
	private int goodsId;
	/**数量*/
	private int num;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getGoodsType() {
		return goodsType;
	}
	public void setGoodsType(int goodsType) {
		this.goodsType = goodsType;
	}
	public int getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(int goodsId) {
		this.goodsId = goodsId;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	
}
