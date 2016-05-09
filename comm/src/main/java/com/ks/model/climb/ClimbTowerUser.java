package com.ks.model.climb;

import java.io.Serializable;
import java.util.Date;

/**
 * 爬塔试炼用户爬塔层数记录
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2015年11月13日
 */
public class ClimbTowerUser implements Serializable {

    private static final long serialVersionUID = 9110121928714473670L;
    
	/**ID*/
	private int id;
	/**玩家编号*/
	private int userId;
	/**试练塔第几层*/
    private int towerFloor;
    /**通关星级*/
    private short startCount;
    /**当天已经战斗次数*/
    private short fightCount;
    /**当天购买战斗次数*/
    private short buyFightCount;
    /**次数数据刷新时间*/
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
	public short getStartCount() {
		return startCount;
	}
	public void setStartCount(short startCount) {
		this.startCount = startCount;
	}
	public short getFightCount() {
		return fightCount;
	}
	public void setFightCount(short fightCount) {
		this.fightCount = fightCount;
	}
	public short getBuyFightCount() {
		return buyFightCount;
	}
	public void setBuyFightCount(short buyFightCount) {
		this.buyFightCount = buyFightCount;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
