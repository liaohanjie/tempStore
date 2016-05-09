package com.ks.model.buding;

import java.io.Serializable;

/**
 * 建筑
 * 
 * @author ks
 */
public class Buding implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final int BUDING_ID_种植园 = 1;
	public static final int BUDING_ID_水生园 = 2;
	public static final int BUDING_ID_矿质洞 = 3;
	public static final int BUDING_ID_主屋 = 4;
	public static final int BUDING_ID_研发屋 = 5;
	public static final int BUDING_ID_武具屋 = 6;
	public static final int BUDING_ID_道具屋 = 7;
	public static final int BUDING_ID_仓库 = 8;

	/** 建筑编号 */
	private int budingId;
	/** 名称 */
	private String name;
	/** 最高等级 */
	private int maxLevel;
	/** 是否能收集 */
	private boolean collect;

	public int getBudingId() {
		return budingId;
	}

	public void setBudingId(int budingId) {
		this.budingId = budingId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getMaxLevel() {
		return maxLevel;
	}

	public void setMaxLevel(int maxLevel) {
		this.maxLevel = maxLevel;
	}

	public boolean isCollect() {
		return collect;
	}

	public void setCollect(boolean collect) {
		this.collect = collect;
	}

}
