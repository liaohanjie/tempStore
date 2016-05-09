package com.ks.model.buding;

import java.io.Serializable;
import java.util.Date;
/**
 * 用户建筑
 * @author ks
 */
public class UserBuding implements Serializable {
	
	private static final long serialVersionUID = 1L;
	/**用户编号*/
	private int userId;
	/**建筑编号*/
	private int budingId;
	/**等级*/
	private int level;
	/**金钱*/
	private int gold;
	/**收取次数*/
	private int collectCount;
	/**最后收取时间*/
	private Date lastCollectTime;
	/**创建时间*/
	private Date createTime;
	/**修改时间*/
	private Date updateTime;
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getBudingId() {
		return budingId;
	}
	public void setBudingId(int budingId) {
		this.budingId = budingId;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getGold() {
		return gold;
	}
	public void setGold(int gold) {
		this.gold = gold;
	}
	public int getCollectCount() {
		return collectCount;
	}
	public void setCollectCount(int collectCount) {
		this.collectCount = collectCount;
	}
	public Date getLastCollectTime() {
		return lastCollectTime;
	}
	public void setLastCollectTime(Date lastCollectTime) {
		this.lastCollectTime = lastCollectTime;
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
