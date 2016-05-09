package com.ks.protocol.vo.goods;

import java.util.List;

import com.ks.protocol.Message;
/**
 * 
 * @author living.li
 * @date 2014年7月14日
 */
public abstract class EqRepairRetVO extends Message {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**受影响的格子*/
	private List<UserGoodsVO> goods;
	/**当前的金币*/
	private int currentGold;
	
	public List<UserGoodsVO> getGoods() {
		return goods;
	}
	public void setGoods(List<UserGoodsVO> goods) {
		this.goods = goods;
	}
	public int getCurrentGold() {
		return currentGold;
	}
	public void setCurrentGold(int currentGold) {
		this.currentGold = currentGold;
	}
	
	
	public void init(List<UserGoodsVO> userGoods,int currGold){
		this.goods=userGoods;
		this.currentGold=currGold;
	}
	

}
