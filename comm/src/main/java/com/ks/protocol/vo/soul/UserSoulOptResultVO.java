package com.ks.protocol.vo.soul;

import java.util.List;

import com.ks.protocol.Message;
import com.ks.protocol.vo.goods.UserGoodsVO;
import com.ks.protocol.vo.user.UserSoulVO;
/**
 * 战魂操作结果
 * @author ks
 */
public class UserSoulOptResultVO extends Message{

	private static final long serialVersionUID = 1L;
	/**类型*/
	private int type;
	/**当前金钱*/
	private int gold;
	/**操作后的战魂*/
	private UserSoulVO soul;
	/**受影响的格子*/
	private List<UserGoodsVO> userGoods;
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getGold() {
		return gold;
	}
	public void setGold(int gold) {
		this.gold = gold;
	}
	public UserSoulVO getSoul() {
		return soul;
	}
	public void setSoul(UserSoulVO soul) {
		this.soul = soul;
	}
	public List<UserGoodsVO> getUserGoods() {
		return userGoods;
	}
	public void setUserGoods(List<UserGoodsVO> userGoods) {
		this.userGoods = userGoods;
	}
}
