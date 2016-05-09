/**
 * 
 */
package com.ks.protocol.vo.activity;

import java.util.List;

import com.ks.protocol.Message;
import com.ks.protocol.vo.items.GoodsVO;

/**
 * @author living.li
 * @date  2014年10月14日 下午6:01:31
 * 限时礼包信息
 */
public class ChargeGiftVO extends Message{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**游戏币数量*/
	private int gameCoin;
	/**物品*/
	private List<GoodsVO> goods;
	/**活动期间已充值的魂币*/
	private int totalCurrency;
	
	public void init(int gameCoin,List<GoodsVO> gifts,int totalCurrency){
		this.gameCoin = gameCoin;
		this.goods =gifts;
		this.totalCurrency = totalCurrency;
	}

	public int getGameCoin() {
		return gameCoin;
	}

	public void setGameCoin(int gameCoin) {
		this.gameCoin = gameCoin;
	}

	public List<GoodsVO> getGoods() {
		return goods;
	}

	public void setGoods(List<GoodsVO> goods) {
		this.goods = goods;
	}

	public int getTotalCurrency() {
		return totalCurrency;
	}

	public void setTotalCurrency(int totalCurrency) {
		this.totalCurrency = totalCurrency;
	}
	
	
}
