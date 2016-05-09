package com.ks.protocol.vo.shop;

import java.util.List;

import com.ks.protocol.Message;

/**
 * 商品信息
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2015年7月16日
 */
public  class  ProductVO extends Message {

	private static final long serialVersionUID = 1L;
	
	private int id;
    /**物品分类编号(1: VIP卡 N: 自定义)*/
    private int classId;
    /**物品类型编号(0-10: 表示VIP等级， 11： 以后自定义)*/
    private int typeId;
    /**购买次数(0: 无限制， N：每天购买数量)*/
    private int buyCount;
    /**商品名称*/
    private String productName;
    /**商品描述*/
    private String productDesc;
    /**价格(魂钻)*/
    private int price;
    /**价格(金币)*/
    private int gold;
    /**数量*/
    private int num;
    /**商品项目信息*/
    List<ProductItemVO> listProductItem;

	public void initProductVO(int id, int classId, int typeId, int buyCount, String productName, String productDesc, int price, int gold, int num, List<ProductItemVO> listProductItem) {
		this.id = id;
	    this.classId = classId;
	    this.typeId = typeId;
	    this.buyCount = buyCount;
	    this.productName = productName;
	    this.productDesc = productDesc;
	    this.price = price;
	    this.gold = gold;
	    this.num = num;
	    this.listProductItem = listProductItem;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getClassId() {
		return classId;
	}

	public void setClassId(int classId) {
		this.classId = classId;
	}

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	public int getBuyCount() {
		return buyCount;
	}

	public void setBuyCount(int buyCount) {
		this.buyCount = buyCount;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductDesc() {
		return productDesc;
	}

	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getGold() {
		return gold;
	}

	public void setGold(int gold) {
		this.gold = gold;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public List<ProductItemVO> getListProductItem() {
		return listProductItem;
	}

	public void setListProductItem(List<ProductItemVO> listProductItem) {
		this.listProductItem = listProductItem;
	}
}