package com.ks.protocol.vo.shop;

import com.ks.protocol.Message;

/**
 * 商品购买次数
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2015年7月16日
 */
public  class  ProductBuyCountVO extends Message {

	private static final long serialVersionUID = 1L;
	
	private int id;
	/**物品类型编号(0-10: 表示VIP等级， 11： 以后自定义)*/
	private int typeId;
	/**已买数量*/
	private int buyCount;
	
	public void initProductBugCountVO(int id, int typeId, int buyCount) {
	    this.id = id;
	    this.typeId = typeId;
	    this.buyCount = buyCount;
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
}