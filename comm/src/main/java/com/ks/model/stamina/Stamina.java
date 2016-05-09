package com.ks.model.stamina;

import java.io.Serializable;

/**
 * 体力配置
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2015年9月18日
 */
public class Stamina implements Serializable {

	private int id;
	/**购买次数*/
	private int buyCount;
	/**花费魂钻*/
	private int price;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getBuyCount() {
		return buyCount;
	}
	public void setBuyCount(int buyCount) {
		this.buyCount = buyCount;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
}
