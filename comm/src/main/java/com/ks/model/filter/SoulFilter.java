package com.ks.model.filter;


public class SoulFilter extends Filter{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 游戏编号 */
	private Integer soulId;

	/** 最高LV */
	private Integer lvMax;
	
	/** 产出经验 */
	private Integer giveExp;

	public Integer getSoulId() {
		return soulId;
	}

	public void setSoulId(Integer soulId) {
		this.soulId = soulId;
	}

	public Integer getLvMax() {
		return lvMax;
	}

	public void setLvMax(Integer lvMax) {
		this.lvMax = lvMax;
	}

	public Integer getGiveExp() {
		return giveExp;
	}

	public void setGiveExp(Integer giveExp) {
		this.giveExp = giveExp;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}




	
	
}
