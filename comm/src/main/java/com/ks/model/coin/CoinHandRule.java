package com.ks.model.coin;

import java.io.Serializable;

import com.ks.model.Weight;

/**
 * 点金手权重
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2015年9月11日
 */
public class CoinHandRule implements Weight, Serializable {

    private static final long serialVersionUID = -1761992159661227387L;
    
	private int id;
	/**点金手对应编号*/
	private int coinHandId;
	/**几倍*/
	private int times;
	/**权重*/
	private int weight;
	/**总权重*/
	private int totalWeight;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCoinHandId() {
		return coinHandId;
	}
	public void setCoinHandId(int coinHandId) {
		this.coinHandId = coinHandId;
	}
	public int getTimes() {
		return times;
	}
	public void setTimes(int times) {
		this.times = times;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public int getTotalWeight() {
		return totalWeight;
	}
	public void setTotalWeight(int totalWeight) {
		this.totalWeight = totalWeight;
	}
}
