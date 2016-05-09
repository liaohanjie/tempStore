/**
 * 
 */
package com.ks.protocol.vo.activity;

import java.util.List;

import com.ks.model.activity.FlashGiftBag;
import com.ks.protocol.Message;
import com.ks.protocol.vo.items.GoodsVO;


/**
 * @author living.li
 * @date  2014年10月17日 下午4:55:30
 *
 *
 */
public class FlashGiftBagVO extends Message {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;
	/**最大数量*/
	private int maxNum;
	/**已经购买数量*/
	private int buyNum;
	/**魂币价格*/
	private int price;
	/**物品*/
	private List<GoodsVO> goods;
	
	
	public void init(FlashGiftBag o,List<GoodsVO> goods){
		this.id = o.getId();
		this.maxNum = o.getMaxNum();
		this.buyNum = o.getBuyNum();
		this.price = o.getPrice();
		this.goods = goods;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getMaxNum() {
		return maxNum;
	}
	public void setMaxNum(int maxNum) {
		this.maxNum = maxNum;
	}

	public int getBuyNum() {
		return buyNum;
	}

	public void setBuyNum(int buyNum) {
		this.buyNum = buyNum;
	}

	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public List<GoodsVO> getGoods() {
		return goods;
	}
	public void setGoods(List<GoodsVO> goods) {
		this.goods = goods;
	}
	
	
}
