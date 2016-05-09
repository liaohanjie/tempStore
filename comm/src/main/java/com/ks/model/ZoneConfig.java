package com.ks.model;

import java.io.Serializable;
import java.util.Date;
/**
 * 大区配置
 * @author ks
 */
public class ZoneConfig implements Serializable {

	private static final long serialVersionUID = 1L;
	/**用户种子配置*/
	public static final int ID_USER_ID_SEED = 1;
	
	/**配置编号*/
	private int id;
	/**配置值*/
	private int val;
	/**创建时间*/
	private Date createTime;
	/**修改时间*/
	private Date updateTime;
	/**活动战魂仓库价格*/
	private int acSoulCapacityPrice;
	/**活动好友上限价格*/
	private int acFriendCapacityPrice;
	/**活动包裹格子价格*/
	private int acItemCapacityPrice;
	/**活动体力价格*/
	private int acStaminaPrice;
	/**活动竞技点价格*/
	private int acAthleticsPoint;
	/***/
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getVal() {
		return val;
	}
	public void setVal(int val) {
		this.val = val;
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
	public int getAcSoulCapacityPrice() {
		return acSoulCapacityPrice;
	}
	public void setAcSoulCapacityPrice(int acSoulCapacityPrice) {
		this.acSoulCapacityPrice = acSoulCapacityPrice;
	}
	public int getAcFriendCapacityPrice() {
		return acFriendCapacityPrice;
	}
	public void setAcFriendCapacityPrice(int acFriendCapacityPrice) {
		this.acFriendCapacityPrice = acFriendCapacityPrice;
	}
	public int getAcItemCapacityPrice() {
		return acItemCapacityPrice;
	}
	public void setAcItemCapacityPrice(int acItemCapacityPrice) {
		this.acItemCapacityPrice = acItemCapacityPrice;
	}
	public int getAcStaminaPrice() {
		return acStaminaPrice;
	}
	public void setAcStaminaPrice(int acStaminaPrice) {
		this.acStaminaPrice = acStaminaPrice;
	}
	public int getAcAthleticsPoint() {
		return acAthleticsPoint;
	}
	public void setAcAthleticsPoint(int acAthleticsPoint) {
		this.acAthleticsPoint = acAthleticsPoint;
	}
	
	
}
