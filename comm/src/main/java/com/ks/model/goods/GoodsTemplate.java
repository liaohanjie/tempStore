package com.ks.model.goods;

import java.io.Serializable;

public class GoodsTemplate implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**战魂*/
	public static final int TYPE_SOUL = 1;
	/**道具*/
	public static final int TYPE_PROP = 2;
	/**材料*/
	public static final int TYPE_STUFF = 3;
	/**装备*/
	public static final int TYPE_EQUIPMENT = 4;
	/**金币*/
	public static final int TYPE_GOLD = 5;
	/**魂钻*/
	public static final int TYPE_CURRENCY = 6;
	/**经验*/
	public static final int TYPE_EXP = 7;
	/**友情点*/
	public static final int TYPE_FRIENDLY_POINT = 8;
	/**宝箱*/
	public static final int TYPE_BOX = 9;
	/**怪物*/
	public static final int TYPE_MONSTER = 10;
	/**副本道具*/
	public static final int TYPE_BAK_PROP = 11;
	private int goodsId;
	private String name;
	private int type;
	private int level;

	public int getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(int goodsId) {
		this.goodsId = goodsId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

}
