/**
 * 
 */
package com.ks.protocol.vo.goods;

import java.util.List;

import com.ks.protocol.Message;
import com.ks.protocol.vo.user.UserSoulVO;

/**
 * @author living.li
 * @date  2015年3月30日 下午7:15:52
 *
 *
 */
public class GuideGiftVO extends Message {

	/** */
	private static final long serialVersionUID = 1L;
	
	private int gold;
	
	private UserSoulVO soulGift;

	private List<UserGoodsVO> userGoods;
	
	public List<UserGoodsVO> getUserGoods() {
		return userGoods;
	}

	public void setUserGoods(List<UserGoodsVO> userGoods) {
		this.userGoods = userGoods;
	}

	public int getGold() {
		return gold;
	}

	public void setGold(int gold) {
		this.gold = gold;
	}

	public UserSoulVO getSoulGift() {
		return soulGift;
	}

	public void setSoulGift(UserSoulVO soulGift) {
		this.soulGift = soulGift;
	}
	public void init(int gold,UserSoulVO soulVo){
		this.gold=gold;
		this.soulGift=soulVo;
	}
	

}
