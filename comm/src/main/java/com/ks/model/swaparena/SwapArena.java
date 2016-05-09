package com.ks.model.swaparena;
/**
 * 交换排名竞技赛
 * @author hanjie.l
 *
 */
public class SwapArena {
	
	/**
	 * 每日免费挑战次数
	 */
	public static final int FREE_TIMES = 10;
	
	/**
	 * 主键id
	 */
	private int id;
	
	/**
	 * 玩家id
	 */
	private int userId;
	
	/**
	 * 排名
	 */
	private int rank;
	
	/**
	 * 可挑战次数
	 */
	private int times;
	
	/**
	 * 今日购买次数
	 */
	private int buyTimes;
	
	/**
	 * 下次可以战斗的时间
	 */
	private long nextFightTime;
	
	/**
	 * 上次更新时间
	 */
	private long lastUpdateTime;
	
	/**
	 * 是否为机器
	 */
	private boolean robot;

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

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public int getTimes() {
		return times;
	}

	public void setTimes(int times) {
		this.times = times;
	}

	public int getBuyTimes() {
		return buyTimes;
	}

	public void setBuyTimes(int buyTimes) {
		this.buyTimes = buyTimes;
	}

	public long getNextFightTime() {
		return nextFightTime;
	}

	public void setNextFightTime(long nextFightTime) {
		this.nextFightTime = nextFightTime;
	}

	public long getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(long lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	
	public boolean isRobot() {
		return robot;
	}

	public void setRobot(boolean robot) {
		this.robot = robot;
	}

	/**
	 * 重置
	 */
	public void reset(){
		this.lastUpdateTime = System.currentTimeMillis();
		this.buyTimes = 0;
		this.times = times > FREE_TIMES? times : FREE_TIMES;
	}
}
