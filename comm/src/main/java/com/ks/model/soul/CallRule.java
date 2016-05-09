package com.ks.model.soul;

import java.io.Serializable;

import com.ks.model.Weight;

/**
 * 战魂召唤分组
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2015年9月10日
 */
public class CallRule implements Weight, Serializable {

	private static final long serialVersionUID = 1L;
	
	/**召唤类型, 1:友情点 */
	public static final int TYPE_ID_FRIEND_POINT = 1;
	/**召唤类型, 2:魂钻*/
	public static final int TYPE_ID_CURRENCY = 2;
	
	/**是否保底优先 1: 是*/
	public static final int FIRST_YES = 1;
	/**是否保底优先  0: 否*/
	public static final int FIRST_NO = 0;
	/**优先因子*/
	public static final int FIRST_FACTOR = 10;
	
	
	private int id;
	/**召唤类型, 1:友情点 2:魂钻*/
	private int typeId;
	/**是否保底优先 0: 否 1: 是*/
	private int first;
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
	public int getTotalWeight() {
		return totalWeight;
	}
	public void setTotalWeight(int totalWeight) {
		this.totalWeight = totalWeight;
	}
	public int getTypeId() {
		return typeId;
	}
	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}
	public int getFirst() {
		return first;
	}
	public void setFirst(int first) {
		this.first = first;
	}
}
