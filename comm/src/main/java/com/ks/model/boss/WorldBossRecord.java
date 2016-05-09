package com.ks.model.boss;
/**
 * boss记录
 * @author hanjie.l
 *
 */
public class WorldBossRecord {
	
	/**
	 * id
	 */
	private int bossId;
	
	/**
	 * 版本
	 */
	private String version="";
	
	/**
	 * 等级
	 */
	private int level;
	
	/**
	 * 当前血量
	 */
	private long curBlood;
	
	/**
	 * 最大血量
	 */
	private long maxBlood;
	
	/**
	 * 是否开启中
	 */
	private boolean open;
	
	/**
	 * 结束时间
	 */
	private long endTime;
	
	/**
	 * 下次开启时间
	 */
	private long nextBeginTime;
	
	public void init(BossSetting bossSetting){
		this.bossId = bossSetting.getBossId();
		this.level = bossSetting.getLevel();
		this.curBlood = bossSetting.getBlood();
		this.maxBlood = bossSetting.getBlood();
	}

	public int getBossId() {
		return bossId;
	}

	public void setBossId(int bossId) {
		this.bossId = bossId;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public long getCurBlood() {
		return curBlood;
	}

	public void setCurBlood(long curBlood) {
		this.curBlood = curBlood;
	}

	public long getMaxBlood() {
		return maxBlood;
	}

	public void setMaxBlood(long maxBlood) {
		this.maxBlood = maxBlood;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	public long getNextBeginTime() {
		return nextBeginTime;
	}

	public void setNextBeginTime(long nextBeginTime) {
		this.nextBeginTime = nextBeginTime;
	}
}
