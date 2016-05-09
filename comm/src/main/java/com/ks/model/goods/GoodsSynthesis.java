package com.ks.model.goods;

import java.io.Serializable;
import java.util.List;

public class GoodsSynthesis implements Serializable {

	private static final long serialVersionUID = 1L;
	/**编号*/
	private int id;
	/**物品类型*/
	private int goodsType;
	/**物品编号*/
	private int goodsId;
	/**建筑等级*/
	private int budingLevel;
	/**建筑规则*/
	private List<GoodsSynthesisRule> rules;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getGoodsType() {
		return goodsType;
	}
	public void setGoodsType(int goodsType) {
		this.goodsType = goodsType;
	}
	public int getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(int goodsId) {
		this.goodsId = goodsId;
	}
	public int getBudingLevel() {
		return budingLevel;
	}
	public void setBudingLevel(int budingLevel) {
		this.budingLevel = budingLevel;
	}
	public List<GoodsSynthesisRule> getRules() {
		return rules;
	}
	public void setRules(List<GoodsSynthesisRule> rules) {
		this.rules = rules;
	}
	
}
