package com.ks.model.climb;

import java.io.Serializable;
import java.util.Date;

/**
 * 爬塔试炼集星奖励领取记录
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2015年11月13日
 */
public class ClimbTowerAwardRecord implements Serializable {

    private static final long serialVersionUID = 230610200585984354L;
    
	/**ID*/
	private int id;
	/**玩家编号*/
	private int userId;
	/**星星区域*/
	private short starRegion;
	/**通关星级总数*/
    private int starNum;
    /**领取状态 (0: 未领取， 1: 领取)*/
    //private int status;
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
//	public int getStatus() {
//		return status;
//	}
//	public void setStatus(int status) {
//		this.status = status;
//	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
