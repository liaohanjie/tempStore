package com.ks.protocol.vo.tower;

import com.ks.model.climb.ClimbTowerAwardRecord;
import com.ks.protocol.Message;

/**
 * 爬塔记录奖励
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2015年12月4日
 */
public class ClimbTowerAwardRecordVO extends Message {

    
    private static final long serialVersionUID = -7522653045770803242L;
    
    /**星星区域*/
	private short starRegion;
	/**星星数量*/
	private int starNum; 

	public void init(ClimbTowerAwardRecord o) {
		this.starRegion = o.getStarRegion();
		this.starNum = o.getStarNum();
	}

	public short getStarRegion() {
		return starRegion;
	}

	public void setStarRegion(short starRegion) {
		this.starRegion = starRegion;
	}

	public int getStarNum() {
		return starNum;
	}

	public void setStarNum(int starNum) {
		this.starNum = starNum;
	}

}
