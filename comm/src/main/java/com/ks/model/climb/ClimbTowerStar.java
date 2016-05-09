package com.ks.model.climb;

import java.io.Serializable;

/**
 * 爬塔试炼星级配置
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2015年11月13日
 */
public class ClimbTowerStar implements Serializable {

    private static final long serialVersionUID = 3898729130723538755L;
    
	/**星星区域*/
	private int startRegion;
	/**星星总数*/
	private int startNum;
	/**奖励*/
	private int awardId;
	
	public int getStartRegion() {
		return startRegion;
	}
	public void setStartRegion(int startRegion) {
		this.startRegion = startRegion;
	}
	public int getStartNum() {
		return startNum;
	}
	public void setStartNum(int startNum) {
		this.startNum = startNum;
	}
	public int getAwardId() {
		return awardId;
	}
	public void setAwardId(int awardId) {
		this.awardId = awardId;
	}
}
