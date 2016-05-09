package com.ks.protocol.vo.mission;

import java.util.List;

import com.ks.protocol.Message;
import com.ks.protocol.vo.goods.UserBakPropVO;
import com.ks.protocol.vo.goods.UserGoodsVO;
import com.ks.protocol.vo.user.UserSoulVO;

public class UserAwardVO extends Message {

	private static final long serialVersionUID = 1L;
	/**用户战魂*/
	private List<UserSoulVO> userSouls;
	/**用户物品*/
	private List<UserGoodsVO> userGoodses;
	/**金币*/
	private int gold;
	/**经验*/
	private int exp;
	/**货币*/
	private int currency;
	/**等级*/
	private int level;	
	/**友情点*/
	private int friendlyPoint;
	/**体力*/
	private int stamina;
	/**最后恢复体力时间*/
	private long lastRegainStaminaTime;
	/**副本道具*/
	private  List<UserBakPropVO> bakProp;
	/**删除战魂*/
	private List<UserSoulVO> deleteSouls;
	/**积分*/
	private int point;
	/**荣誉值*/
	private int honor;
	
	public List<UserBakPropVO> getBakProp() {
		return bakProp;
	}
	public void setBakProp(List<UserBakPropVO> bakProp) {
		this.bakProp = bakProp;
	}
	public int getStamina() {
		return stamina;
	}
	public void setStamina(int stamina) {
		this.stamina = stamina;
	}
	public long getLastRegainStaminaTime() {
		return lastRegainStaminaTime;
	}
	public void setLastRegainStaminaTime(long lastRegainStaminaTime) {
		this.lastRegainStaminaTime = lastRegainStaminaTime;
	}
	public int getFriendlyPoint() {
		return friendlyPoint;
	}
	public void setFriendlyPoint(int friendlyPoint) {
		this.friendlyPoint = friendlyPoint;
	}
	public List<UserSoulVO> getUserSouls() {
		return userSouls;
	}
	public void setUserSouls(List<UserSoulVO> userSouls) {
		this.userSouls = userSouls;
	}
	public List<UserGoodsVO> getUserGoodses() {
		return userGoodses;
	}
	public void setUserGoodses(List<UserGoodsVO> userGoodses) {
		this.userGoodses = userGoodses;
	}
	public int getGold() {
		return gold;
	}
	public void setGold(int gold) {
		this.gold = gold;
	}
	public int getExp() {
		return exp;
	}
	public void setExp(int exp) {
		this.exp = exp;
	}
	public int getCurrency() {
		return currency;
	}
	public void setCurrency(int currency) {
		this.currency = currency;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public List<UserSoulVO> getDeleteSouls() {
		return deleteSouls;
	}
	public void setDeleteSouls(List<UserSoulVO> deleteSouls) {
		this.deleteSouls = deleteSouls;
	}
	public int getPoint() {
		return point;
	}
	public void setPoint(int point) {
		this.point = point;
	}
	public int getHonor() {
		return honor;
	}
	public void setHonor(int honor) {
		this.honor = honor;
	}
}
