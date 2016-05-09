package com.ks.model.totem;

import java.io.Serializable;

import com.ks.model.Weight;

/**
 * 神木图腾战魂重塑规则对应战魂概率
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2015年9月9日
 */
public class TotemSoul implements Weight, Serializable {

	private int id;
	/**战魂编号*/
	private int soulId;
	/**战魂稀有度/战魂星级*/
	private int soulRare;
	/**权重*/
    private int weight;
    /**权重和*/
    private int totalWeight;
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getSoulId() {
		return soulId;
	}
	public void setSoulId(int soulId) {
		this.soulId = soulId;
	}
	public int getSoulRare() {
		return soulRare;
	}
	public void setSoulRare(int soulRare) {
		this.soulRare = soulRare;
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
