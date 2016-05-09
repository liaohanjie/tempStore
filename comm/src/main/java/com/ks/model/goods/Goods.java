package com.ks.model.goods;

import java.io.Serializable;

/**
 * 物品
 * 
 * @author ks
 */
public class Goods implements Serializable,Cloneable {

	private static final long serialVersionUID = 1L;
	/** 战魂 */
	public static final int TYPE_SOUL = 1;
	/** 道具 */
	public static final int TYPE_PROP = 2;
	/** 材料 */
	public static final int TYPE_STUFF = 3;
	/** 装备 */
	public static final int TYPE_EQUIPMENT = 4;
	/** 金币 */
	public static final int TYPE_GOLD = 5;
	/** 魂钻 */
	public static final int TYPE_CURRENCY = 6;
	/** 经验 */
	public static final int TYPE_EXP = 7;
	/** 友情点 */
	public static final int TYPE_FRIENDLY_POINT = 8;
	/** 宝箱 */
	public static final int TYPE_BOX = 9;
	/** 怪物 */
	public static final int TYPE_MONSTER = 10;
	/** 副本道具 */
	public static final int TYPE_BAK_PROP = 11;
	/** 体力 */
	public static final int TYPE_STAMINA = 12;

	/** 3星随机经验战魂 */
	public static final int TYPE_THREE_LEVEL_RANDOM_EXPSOUL = 13;

	/** 4星随机经验战魂 */
	public static final int TYPE_FOUR_LEVEL__RANDOM_EXPSOUL = 14;

	/** 5星随机经验战魂 */
	public static final int TYPE_FIVE_LEVEL_RANDOM_EXPSOUL = 15;
	
	/**积分点*/
	public static final int TYPE_POINT = 16;
	
	/**荣誉值*/
	public static final int TYPE_HONOR = 17;

	/** 物品编号 */
	private int goodsId;
	/** 物品类型 */
	private int type;
	/** 物品数量 */
	private int num;
	/** 等级 */
	private int level;

	public static Goods create(int goodsId, int type, int num, int level) {
		Goods g = new Goods();
		g.setGoodsId(goodsId);
		g.setNum(num);
		g.setLevel(level);
		g.setType(type);
		return g;
	}

	public int getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(int goodsId) {
		this.goodsId = goodsId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
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

	@Override
	protected Goods clone() {
		try {
			return (Goods) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}
}
