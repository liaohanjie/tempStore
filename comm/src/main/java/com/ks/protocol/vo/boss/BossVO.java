package com.ks.protocol.vo.boss;

import com.ks.model.boss.WorldBossRecord;
import com.ks.protocol.Message;

/**
 * boss传输对象
 * @author hanjie.l
 *
 */
public class BossVO extends Message{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2454567793186126356L;

	/**
	 * id
	 */
	private int bossId;
	
	/**
	 * 等级
	 */
	private int level;
	
	/**
	 * 当前的血量
	 */
	private long curBlood;
	
	/**
	 * 最大血量
	 */
	private long maxBlood;
	
	public void init(WorldBossRecord bossRecord){
		this.bossId = bossRecord.getBossId();
		this.level = bossRecord.getLevel();
		this.curBlood = bossRecord.getCurBlood();
		this.maxBlood = bossRecord.getMaxBlood();
	}

	public int getBossId() {
		return bossId;
	}

	public void setBossId(int bossId) {
		this.bossId = bossId;
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
}                 