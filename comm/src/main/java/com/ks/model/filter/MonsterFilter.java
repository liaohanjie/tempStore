package com.ks.model.filter;

public class MonsterFilter extends Filter {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**怪物ID*/
	private Integer monsterId;	
	
	/** 怪物名称 */
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getMonsterId() {
		return monsterId;
	}

	public void setMonsterId(Integer monsterId) {
		this.monsterId = monsterId;
	}
}
