package com.ks.protocol.vo.achieve;

import java.util.List;

import com.ks.protocol.Message;
import com.ks.protocol.vo.goods.UserBakPropVO;
import com.ks.protocol.vo.goods.UserGoodsVO;
import com.ks.protocol.vo.user.UserSoulVO;
/**
 * 
 * @author living.li
 * @date  2014年4月26日
 */
public class AchieveAwardVO extends Message{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**用户战魂*/
	private List<UserSoulVO> userSouls;
	/**用户物品*/
	private List<UserGoodsVO> goods;
	
	/**当前金币*/
	private int gold;
	/**当前货币*/
	private int currency;
	/**当前友情点*/
	private int friendlyPoint;	
	/**副本道具*/
	private List<UserBakPropVO> bakProps;
	
	
	public List<UserBakPropVO> getBakProps() {
		return bakProps;
	}
	public void setBakProps(List<UserBakPropVO> bakProps) {
		this.bakProps = bakProps;
	}
	public List<UserSoulVO> getUserSouls() {
		return userSouls;
	}
	public void setUserSouls(List<UserSoulVO> userSouls) {
		this.userSouls = userSouls;
	}
	public List<UserGoodsVO> getGoods() {
		return goods;
	}
	public void setGoods(List<UserGoodsVO> goods) {
		this.goods = goods;
	}
	public int getGold() {
		return gold;
	}
	public void setGold(int gold) {
		this.gold = gold;
	}
	public int getCurrency() {
		return currency;
	}
	public void setCurrency(int currency) {
		this.currency = currency;
	}
	public int getFriendlyPoint() {
		return friendlyPoint;
	}
	public void setFriendlyPoint(int friendlyPoint) {
		this.friendlyPoint = friendlyPoint;
	}
}
