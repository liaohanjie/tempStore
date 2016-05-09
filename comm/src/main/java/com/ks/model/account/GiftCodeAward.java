package com.ks.model.account;

import java.io.Serializable;

public class GiftCodeAward implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**礼包ID*/
	private int giftId;
	
	/**奖励ID*/
	private String awardId;

	/** 物品编号 */
	private int assId;

	/** 物品类型 */
	private int goodsType;

	/** 物品数量 */
	private int num;

	/** 等级 */
	private int level;

	public String getAwardId() {
		return awardId;
	}

	public void setAwardId(String awardId) {
		this.awardId = awardId;
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

	public int getGiftId() {
	    return giftId;
    }

	public void setGiftId(int giftId) {
	    this.giftId = giftId;
    }

}
