package com.ks.protocol.vo.goods;

import java.util.List;

import com.ks.protocol.Message;
import com.ks.protocol.vo.user.UserSoulVO;
/**
 * 
 * @author living.li
 *	
 */
public class GainAwardVO extends Message {

	private static final long serialVersionUID = 1L;
	/**当前金币*/
	private int gold;
	/**当前货币*/
	private int currency;
	/**当前友情点*/
	private int friendlyPoint;
	/**当前积分*/
	private int point;
	/**当前荣誉值*/
	private int honor;
	/**收到的战魂*/
	private List<UserSoulVO> souls;
	/**受影响的格子*/
	private List<UserGoodsVO> goodses;
	/** 增加的积分*/
    private int addPoint;
    /**增加金币*/
    private int addGold;
    /**增加魂钻*/
    private int addCurrency;
    /**增加荣誉值*/
    private int addHonnor;
	
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
	public List<UserGoodsVO> getGoodses() {
		return goodses;
	}
	public void setGoodses(List<UserGoodsVO> goodses) {
		this.goodses = goodses;
	}
	public List<UserSoulVO> getSouls() {
		return souls;
	}
	public void setSouls(List<UserSoulVO> souls) {
		this.souls = souls;
	}
	public int getAddPoint() {
		return addPoint;
	}
	public void setAddPoint(int addPoint) {
		this.addPoint = addPoint;
	}
	public int getAddGold() {
		return addGold;
	}
	public void setAddGold(int addGold) {
		this.addGold = addGold;
	}
	public int getAddCurrency() {
		return addCurrency;
	}
	public void setAddCurrency(int addCurrency) {
		this.addCurrency = addCurrency;
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
	public int getAddHonnor() {
		return addHonnor;
	}
	public void setAddHonnor(int addHonnor) {
		this.addHonnor = addHonnor;
	}
}
