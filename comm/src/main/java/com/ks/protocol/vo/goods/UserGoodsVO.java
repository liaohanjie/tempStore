package com.ks.protocol.vo.goods;

import com.ks.model.goods.UserGoods;
import com.ks.protocol.Message;

public class UserGoodsVO extends Message {

	private static final long serialVersionUID = 1L;
	
	/**格子编号*/
	private int gridId;
	/**物品类型*/
	private int goodsType;
	/**物品编号*/
	private int goodsId;
	/**物品数量*/
	private int num;
	/**使用次数*/
	private int durable;
	/**用户战魂编号*/
	private long userSoulId;
	/**装备技能ID*/
	private int eqSkillId;
	/**装备技能等级*/
	private int eqSkillLevel;
	
	
	public void init(UserGoods o) {
		this.gridId = o.getGridId();
		this.goodsType = o.getGoodsType();
		this.goodsId = o.getGoodsId();
		this.num = o.getNum();
		this.durable = o.getDurable();
		this.userSoulId = o.getUserSoulId();
		this.eqSkillId=o.getEqSkillId();
		this.eqSkillLevel = o.getEqSkillLevel();
	}
	
	public int getEqSkillId() {
		return eqSkillId;
	}

	public void setEqSkillId(int eqSkillId) {
		this.eqSkillId = eqSkillId;
	}

	public int getGridId() {
		return gridId;
	}
	public void setGridId(int gridId) {
		this.gridId = gridId;
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
	public int getDurable() {
		return durable;
	}
	public void setDurable(int durable) {
		this.durable = durable;
	}
	public long getUserSoulId() {
		return userSoulId;
	}
	public void setUserSoulId(long userSoulId) {
		this.userSoulId = userSoulId;
	}

	public int getEqSkillLevel() {
		return eqSkillLevel;
	}

	public void setEqSkillLevel(int eqSkillLevel) {
		this.eqSkillLevel = eqSkillLevel;
	}
}
