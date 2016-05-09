package com.ks.model.goods;

import java.io.Serializable;
import java.util.Date;
/**
 * 用户物品
 * @author ks
 */
public class UserGoods implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**道具*/
	public static final int GOODS_TYPE_ITEM = 2;
	/**材料*/
	public static final int GOODS_TYPE_STUFF = 3;
	/**装备*/
	public static final int GOODS_TYPE_EQUIPMENT = 4;

	
	/**最大叠加数*/
	public static final int MAX_NUM = 99;
	
	/**格子编号*/
	private int gridId;
	/**用户编号*/
	private int userId;
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
	/**创建时间*/
	private Date createTime;
	/**修改时间*/
	private Date updateTime;
	/**装备技能ID*/
	private int eqSkillId;
	/**装备技能等级*/
	private int eqSkillLevel;
	/**是否修改过*/
	private boolean update;
	
	
	public int getGridId() {
		return gridId;
	}
	public void setGridId(int gridId) {
		this.gridId = gridId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
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
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public boolean isUpdate() {
		return update;
	}
	public void setUpdate(boolean update) {
		this.update = update;
	}
	public int getEqSkillId() {
		return eqSkillId;
	}
	public void setEqSkillId(int eqSkillId) {
		this.eqSkillId = eqSkillId;
	}
	public int getEqSkillLevel() {
		return eqSkillLevel;
	}
	public void setEqSkillLevel(int eqSkillLevel) {
		this.eqSkillLevel = eqSkillLevel;
	}
	@Override
	public String toString() {
		return "UserGoods [gridId=" + gridId + ", goodsType=" + goodsType
				+ ", goodsId=" + goodsId + ", num=" + num + ", durable="
				+ durable + ", userSoulId=" + userSoulId + ", eqSkillId="
				+ eqSkillId + ", eqskillLevel" + eqSkillLevel +"]";
	}
	
	
}
