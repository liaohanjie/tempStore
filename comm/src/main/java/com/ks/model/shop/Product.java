package com.ks.model.shop;

import java.io.Serializable;
import java.util.Date;

/**
 * 商品
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2015年7月14日
 */
public class Product implements Serializable {

    private static final long serialVersionUID = 1;
    
    /**类型定义*/
    /**VIP 1 - 9*/
    public final static int TYPE_VIP_PACK_1 = 1;
    public final static int TYPE_VIP_PACK_2 = 2;
    public final static int TYPE_VIP_PACK_3 = 3;
    public final static int TYPE_VIP_PACK_4 = 4;
    public final static int TYPE_VIP_PACK_5 = 5;
    public final static int TYPE_VIP_PACK_6 = 6;
    public final static int TYPE_VIP_PACK_7 = 7;
    public final static int TYPE_VIP_PACK_8 = 8;
    public final static int TYPE_VIP_PACK_9 = 9;
    
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
    /**状态(0: 删除, 1: 正常)*/
    private byte status;
    /**创建时间*/
    private Date createTime;
    
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
	public byte getStatus() {
		return status;
	}
	public void setStatus(byte status) {
		this.status = status;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
