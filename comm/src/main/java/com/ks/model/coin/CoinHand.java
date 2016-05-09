package com.ks.model.coin;

import java.io.Serializable;

/**
 * 点金手
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2015年9月11日
 */
public class CoinHand implements Serializable {

	private int id;
	/**当前第几次使用*/
	private int num;
	/**金币基数*/
	private int baseGold;
	/**价格(魂钻数量)*/
	private int price;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public int getBaseGold() {
		return baseGold;
	}
	public void setBaseGold(int baseGold) {
		this.baseGold = baseGold;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
}
