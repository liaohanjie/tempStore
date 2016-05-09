package com.ks.model.boss;

import java.io.Serializable;

/**
 * 查看boss开启状态的结果
 * @author hanjie.l
 *
 */
public class CheckBossOpenResult implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4106641767553082684L;

	/**
	 * id
	 */
	private int bossId;
	
	/**
	 * 当前版本
	 */
	private String version;
	
	/**
	 * 活动是否开启
	 */
	private boolean open = false;
	
	/**
	 * 活动结束时间
	 */
	private long endTime;

	/**
	 * 活动下次开启时间
	 */
	private long nextOpenTime;

	public int getBossId() {
		return bossId;
	}

	public void setBossId(int bossId) {
		this.bossId = bossId;
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

	public long getNextOpenTime() {
		return nextOpenTime;
	}

	public void setNextOpenTime(long nextOpenTime) {
		this.nextOpenTime = nextOpenTime;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
}
