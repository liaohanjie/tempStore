package com.ks.protocol.vo.buding;

import com.ks.model.buding.UserBuding;
import com.ks.protocol.Message;

public class UserBudingVO extends Message {

	private static final long serialVersionUID = 1L;
	/**建筑编号*/
	private int budingId;
	/**等级*/
	private int level;
	/**金钱*/
	private int gold;
	/**收取次数*/
	private int collectCount;
	/**最后收取时间*/
	private long lastCollectTime;
	
	public void init(UserBuding o){
		this.budingId = o.getBudingId();
		this.level = o.getLevel();
		this.gold = o.getGold();
		this.collectCount = o.getCollectCount();
		this.lastCollectTime = o.getLastCollectTime().getTime();
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
	public long getLastCollectTime() {
		return lastCollectTime;
	}
	public void setLastCollectTime(long lastCollectTime) {
		this.lastCollectTime = lastCollectTime;
	}
}
