package com.ks.model.boss;
/**
 * 用户记录
 * @author hanjie.l
 *
 */
public class UserBossRecord {
	
	/**
	 * 玩家id
	 */
	private int userId;
	
	/**
	 * bossId
	 */
	private int curBossId;
	
	/**
	 * 当前打的等级
	 */
	private int curBossLevel;
	
	/**
	 * 当前参加的版本{@link WorldBossRecord#getVersion()}
	 */
	private String version="";
	
	/**
	 * 累计伤害
	 */
	private long totalHurt;
	
	/**
	 * 下次可以挑战时间
	 */
	private long nextFightTime;
	
	/**
	 * 总的鼓舞加成值
	 */
	private int inspiredValue;
	
	/**
	 * 是否已经领取参与奖励
	 */
	private boolean receieveJoin = false;
	
	/**
	 * 是否已经领取排名奖励
	 */
	private boolean receieveRank = false;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getCurBossId() {
		return curBossId;
	}

	public void setCurBossId(int curBossId) {
		this.curBossId = curBossId;
	}

	public int getCurBossLevel() {
		return curBossLevel;
	}

	public void setCurBossLevel(int curBossLevel) {
		this.curBossLevel = curBossLevel;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public long getTotalHurt() {
		return totalHurt;
	}

	public void setTotalHurt(long totalHurt) {
		this.totalHurt = totalHurt;
	}

	public long getNextFightTime() {
		return nextFightTime;
	}

	public void setNextFightTime(long nextFightTime) {
		this.nextFightTime = nextFightTime;
	}

	public int getInspiredValue() {
		return inspiredValue;
	}

	public void setInspiredValue(int inspiredValue) {
		this.inspiredValue = inspiredValue;
	}
	
	public boolean isReceieveJoin() {
		return receieveJoin;
	}

	public void setReceieveJoin(boolean receieveJoin) {
		this.receieveJoin = receieveJoin;
	}

	public boolean isReceieveRank() {
		return receieveRank;
	}

	public void setReceieveRank(boolean receieveRank) {
		this.receieveRank = receieveRank;
	}

	public void clearRecord(String version){
		this.curBossLevel = 0;
		this.inspiredValue = 0;
		this.nextFightTime = 0;
		this.totalHurt = 0;
		this.version = version;
		this.receieveJoin = false;
		this.receieveRank = false;
	}
}
