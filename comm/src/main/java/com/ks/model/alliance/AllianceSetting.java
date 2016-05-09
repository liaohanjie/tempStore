package com.ks.model.alliance;
/**
 * 工会配置
 * @author admin
 *
 */
public class AllianceSetting {
	
	/**
	 * 工会等级
	 */
	private int level;
	
	/**
	 * 容量
	 */
	private int capacity;
	
	/**
	 * 消耗贡献值
	 */
	private int costDevote;
	
	/**
	 * 消耗金币
	 */
	private int costGold;
	
	/**
	 * 是否为最高级
	 */
	private boolean maxLevel;

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public int getCostDevote() {
		return costDevote;
	}

	public void setCostDevote(int costDevote) {
		this.costDevote = costDevote;
	}

	public int getCostGold() {
		return costGold;
	}

	public void setCostGold(int costGold) {
		this.costGold = costGold;
	}

	public boolean isMaxLevel() {
		return maxLevel;
	}

	public void setMaxLevel(boolean maxLevel) {
		this.maxLevel = maxLevel;
	}
}
