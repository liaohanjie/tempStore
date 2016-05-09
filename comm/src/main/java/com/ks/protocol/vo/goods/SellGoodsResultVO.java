package com.ks.protocol.vo.goods;

import java.util.ArrayList;
import java.util.List;

import com.ks.model.goods.UserGoods;
import com.ks.protocol.Message;
import com.ks.protocol.MessageFactory;
/**
 * 卖出物品结果
 * @author ks
 */
public class SellGoodsResultVO extends Message {

	private static final long serialVersionUID = 1L;
	/**受影响的格子*/
	private List<UserGoodsVO> goods;
	/**当前金钱*/
	private int gold;
	
	public void init(List<UserGoods> userGoods,int gold){
		goods = new ArrayList<UserGoodsVO>();
		for(UserGoods ug : userGoods){
			UserGoodsVO vo = MessageFactory.getMessage(UserGoodsVO.class);
			vo.init(ug);
			goods.add(vo);
		}
		this.gold = gold;
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
	
	
}
