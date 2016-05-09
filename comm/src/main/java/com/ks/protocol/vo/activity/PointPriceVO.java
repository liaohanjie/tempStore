/**
 * 
 */
package com.ks.protocol.vo.activity;

import com.ks.protocol.Message;

/**
 * @author living.li
 * @date  2014年10月20日 下午8:39:00
 *活动期间游戏购买各种点数价格
 */
public class PointPriceVO extends Message {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**活动战魂仓库价格*/
	private int soulCapacityPrice;
	/**活动好友上限价格*/
	private int friendCapacityPrice;
	/**活动包裹格子价格*/
	private int itemCapacityPrice;
	/**活动体力价格*/
	private int staminaPrice;
	/**活动竞技点价格*/
	private int athleticsPoint;
	
	
	
	public int getSoulCapacityPrice() {
		return soulCapacityPrice;
	}
	public void setSoulCapacityPrice(int soulCapacityPrice) {
		this.soulCapacityPrice = soulCapacityPrice;
	}
	public int getFriendCapacityPrice() {
		return friendCapacityPrice;
	}
	public void setFriendCapacityPrice(int friendCapacityPrice) {
		this.friendCapacityPrice = friendCapacityPrice;
	}
	public int getItemCapacityPrice() {
		return itemCapacityPrice;
	}
	public void setItemCapacityPrice(int itemCapacityPrice) {
		this.itemCapacityPrice = itemCapacityPrice;
	}
	public int getStaminaPrice() {
		return staminaPrice;
	}
	public void setStaminaPrice(int staminaPrice) {
		this.staminaPrice = staminaPrice;
	}
	public int getAthleticsPoint() {
		return athleticsPoint;
	}
	public void setAthleticsPoint(int athleticsPoint) {
		this.athleticsPoint = athleticsPoint;
	}
	
	
	
}
