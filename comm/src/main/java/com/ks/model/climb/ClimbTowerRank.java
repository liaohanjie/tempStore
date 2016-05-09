package com.ks.model.climb;

import java.io.Serializable;
import java.util.Date;

/**
 * 爬塔试炼排行榜
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2015年11月13日
 */
public class ClimbTowerRank implements Serializable {

    private static final long serialVersionUID = -4557066975877653537L;
    
	/**ID*/
	private int id;
	/**玩家编号*/
	private int userId;
	/**试练塔第几次*/
    private int towerFloor;
    /**通关星级*/
    private int starCount;
    /**更新时间*/
    private Date updateTime;
    /**创建时间*/
    private Date createTime;
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getTowerFloor() {
		return towerFloor;
	}
	public void setTowerFloor(int towerFloor) {
		this.towerFloor = towerFloor;
	}
	public int getStarCount() {
		return starCount;
	}
	public void setStarCount(int starCount) {
		this.starCount = starCount;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}
