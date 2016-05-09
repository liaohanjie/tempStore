package com.ks.model.goods;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.ks.model.user.UserSoul;
import com.ks.protocol.MessageFactory;
import com.ks.protocol.vo.goods.UserBakPropVO;
import com.ks.protocol.vo.goods.UserGoodsVO;
import com.ks.protocol.vo.user.UserSoulVO;
/**
 * 用户物品
 * @author ks
 */
public class IssueGoods implements Serializable {
	
    private static final long serialVersionUID = 2205565472191958589L;
    
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
	/**用户战魂*/
	private List<UserSoul> userSouls;
	/**用户物品*/
	private List<UserGoods> userGoods;
	/**用户副本道具*/
	private List<UserBakProp> userBakProp;

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

	public int getFriendlyPoint() {
		return friendlyPoint;
	}

	public void setFriendlyPoint(int friendlyPoint) {
		this.friendlyPoint = friendlyPoint;
	}

	public int getStamina() {
		return stamina;
	}

	public void setStamina(int stamina) {
		this.stamina = stamina;
	}

	public List<UserSoul> getUserSouls() {
		if (userSouls == null) {
			userSouls = new ArrayList<>();
		}
		return userSouls;
	}

	public void setUserSouls(List<UserSoul> userSouls) {
		this.userSouls = userSouls;
	}

	public List<UserGoods> getUserGoods() {
		if (userGoods == null) {
			userGoods = new ArrayList<>();
		}
		return userGoods;
	}

	public void setUserGoods(List<UserGoods> userGoods) {
		this.userGoods = userGoods;
	}

	public List<UserBakProp> getUserBakProp() {
		if (userBakProp == null) {
			userBakProp = new ArrayList<>();
		}
		return userBakProp;
	}

	public void setUserBakProp(List<UserBakProp> userBakProp) {
		this.userBakProp = userBakProp;
	}
	
	public void addIssueGoods(IssueGoods issueGoods) {
		getUserGoods().addAll(issueGoods.getUserGoods());
		getUserSouls().addAll(issueGoods.getUserSouls());
		getUserBakProp().addAll(issueGoods.getUserBakProp());
	}
	
	public static List<UserSoulVO> createUserSoulVOList(List<UserSoul> userSouls){
		List<UserSoulVO> list = new ArrayList<>();
		for(UserSoul o : userSouls) {
			UserSoulVO vo = MessageFactory.getMessage(UserSoulVO.class);
			vo.init(o);
			
			list.add(vo);
		}
		return list;
	}
	
	public static List<UserGoodsVO> createUserGoodsVOList(List<UserGoods> userGoods){
		List<UserGoodsVO> list = new ArrayList<>();
		for(UserGoods o : userGoods) {
			UserGoodsVO vo = MessageFactory.getMessage(UserGoodsVO.class);
			vo.init(o);
			
			list.add(vo);
		}
		return list;
	}
	
	public static List<UserBakPropVO> createUserBakPropVOList(List<UserBakProp> userBakProps){
		List<UserBakPropVO> list = new ArrayList<>();
		for(UserBakProp o : userBakProps) {
			UserBakPropVO vo = MessageFactory.getMessage(UserBakPropVO.class);
			vo.init(o);
			
			list.add(vo);
		}
		return list;
	}
	
	@Override
    public String toString() {
	    return "IssueGoods [gold=" + gold + ", exp=" + exp + ", currency=" + currency + ", level=" + level + ", friendlyPoint=" + friendlyPoint + ", stamina=" + stamina + ", userSouls=" + userSouls
	            + ", userGoods=" + userGoods + ", userBakProp=" + userBakProp + "]";
    }
}
