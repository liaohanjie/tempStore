package com.ks.protocol.vo.buding;

import java.util.List;

import com.ks.protocol.Message;
import com.ks.protocol.vo.goods.UserGoodsVO;
/**
 * 采集结果
 * @author ks
 */
public class CollectResultVO extends Message {

	private static final long serialVersionUID = 1L;
	/**收影响的格子*/
	private List<UserGoodsVO> userGoods;
	/**收影响的建筑*/
	private UserBudingVO userBuding;
	
	public List<UserGoodsVO> getUserGoods() {
		return userGoods;
	}
	public void setUserGoods(List<UserGoodsVO> userGoods) {
		this.userGoods = userGoods;
	}
	public UserBudingVO getUserBuding() {
		return userBuding;
	}
	public void setUserBuding(UserBudingVO userBuding) {
		this.userBuding = userBuding;
	}
	
}
