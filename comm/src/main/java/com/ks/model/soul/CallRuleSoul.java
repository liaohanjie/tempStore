package com.ks.model.soul;

import java.io.Serializable;

import com.ks.model.Weight;

/**
 * 战魂召唤分组战魂
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2015年9月10日
 */
public class CallRuleSoul implements Weight, Serializable {

	private static final long serialVersionUID = 1L;
	
	private int id;
	/**战魂召唤分组ID*/
	private int callRuleId;
	/**战魂编号*/
	private int soulId;
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
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public int getCallRuleId() {
		return callRuleId;
	}
	public void setCallRuleId(int callRuleId) {
		this.callRuleId = callRuleId;
	}
	public int getSoulId() {
		return soulId;
	}
	public void setSoulId(int soulId) {
		this.soulId = soulId;
	}
	public int getTotalWeight() {
		return totalWeight;
	}
	public void setTotalWeight(int totalWeight) {
		this.totalWeight = totalWeight;
	}
}
