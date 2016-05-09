package com.ks.model.climb;

import java.io.Serializable;

/**
 * 爬塔试炼配置
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2015年11月13日
 */
public class ClimbTower implements Serializable {

    private static final long serialVersionUID = 3898729130723538755L;
    
	/**第几层*/
	private int towerFloor;
	/**等级限制*/
	private int level;
	/**消耗试炼石*/
    private int costRock;
    /**首次通关奖励*/
    private int firstAwardId;
    /**固定通关奖励*/
    private int fixAwardId;
    
	public int getTowerFloor() {
		return towerFloor;
	}
	public void setTowerFloor(int towerFloor) {
		this.towerFloor = towerFloor;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getCostRock() {
		return costRock;
	}
	public void setCostRock(int costRock) {
		this.costRock = costRock;
	}
	public int getFirstAwardId() {
		return firstAwardId;
	}
	public void setFirstAwardId(int firstAwardId) {
		this.firstAwardId = firstAwardId;
	}
	public int getFixAwardId() {
		return fixAwardId;
	}
	public void setFixAwardId(int fixAwardId) {
		this.fixAwardId = fixAwardId;
	}
}
