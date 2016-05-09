package com.ks.protocol.vo.boss;

import java.util.ArrayList;
import java.util.List;
import com.ks.model.boss.BossSetting;
import com.ks.model.boss.UserBossRecord;
import com.ks.model.boss.WorldBossRecord;
import com.ks.model.dungeon.ChapterRound;
import com.ks.protocol.Message;

/**
 * 查询boss开启状态
 * @author hanjie.l
 *
 */
public class WorldBossRecordVO extends Message{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7982331295663075685L;

	/**
	 * 主键bossId
	 */
	private int bossId;

	/**
	 * 地图
	 */
	private String map;
	
	/**
	 * 怪物id
	 */
	private List<Integer> monsters;
	
	/**
	 * boss等级
	 */
	private int level;
	
	/**
	 * 当前的血量
	 */
	private long curBlood;
	
	
	/**
	 * 最大血量值
	 */
	private long maxBlood;
	
	/**
	 * 活动是否开启
	 * 0关闭/1开启
	 */
	private int open;

	/**
	 * 结束时间
	 */
	private long endTime;
	
	/**
	 * 下次开启时间
	 */
	private long nextBeginTime;
	
	//============================================
	
	/**
	 * 对boss造成总的伤害
	 */
	private long totalHurt;
	
	/**
	 * 下次可以战斗时间
	 */
	private long nextFightTime;
	
	/**
	 * 鼓舞加成值(百分值，例如返回50，表示50%加成)
	 */
	private int inspiredValue;
	
	
	public void init(WorldBossRecord bossRecord, BossSetting bossSetting, UserBossRecord userBossRecord){
		this.bossId = bossRecord.getBossId();
		this.map = bossSetting.getMap();
		this.monsters = new ArrayList<>();
		String[] monsterIds = bossSetting.getMonsters().split(ChapterRound.MONSTERS_SPLIT);
		for (int pos = 0; pos < 6; pos++) {
			int monsterId = Integer.valueOf(monsterIds[pos]);
			if (monsterId != 0) {
				monsters.add(monsterId);
			}
		}
		this.level = bossRecord.getLevel();
		this.curBlood = bossRecord.getCurBlood();
		this.maxBlood = bossRecord.getMaxBlood();
		this.open = bossRecord.isOpen()? 1 : 0;
		this.nextBeginTime = bossRecord.getNextBeginTime();
		this.endTime = bossRecord.getEndTime();
		//boss活动结束后，前端要显示所有数值归零。但后端不清除数据
		if(userBossRecord != null && bossRecord.isOpen()){
			this.totalHurt = userBossRecord.getTotalHurt();
			this.nextFightTime = userBossRecord.getNextFightTime();
			this.inspiredValue = userBossRecord.getInspiredValue();
		}
	}

	public int getBossId() {
		return bossId;
	}

	public void setBossId(int bossId) {
		this.bossId = bossId;
	}

	public String getMap() {
		return map;
	}

	public void setMap(String map) {
		this.map = map;
	}

	public List<Integer> getMonsters() {
		return monsters;
	}

	public void setMonsters(List<Integer> monsters) {
		this.monsters = monsters;
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

	public int getOpen() {
		return open;
	}

	public void setOpen(int open) {
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
}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       