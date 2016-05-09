package com.ks.model.boss;

import java.util.List;

import com.ks.model.goods.Goods;
/**
 * boss配置
 * @author hanjie.l
 *
 */
public class BossSetting {

	/**
	 * Id
	 */
	private int bossId;
	
	/**
	 * 等级
	 */
	private int level;
	
	/**
	 * 背景地图
	 */
	private String map;
	
	/**
	 * 怪物
	 */
	private String monsters;
	
	/**
	 * 血量
	 */
	private long blood;
	
	/**
	 * 击杀奖励
	 */
	private List<Goods> killReward;
	
	/**
	 * 伤害
	 */
	private long hurt;
	
	/**
	 * 每造成hurt伤害给这么多奖励
	 */
	private List<Goods> hurtReward;

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

	public String getMap() {
		return map;
	}

	public void setMap(String map) {
		this.map = map;
	}

	public String getMonsters() {
		return monsters;
	}

	public void setMonsters(String monsters) {
		this.monsters = monsters;
	}

	public long getBlood() {
		return blood;
	}

	public void setBlood(long blood) {
		this.blood = blood;
	}

	public List<Goods> getKillReward() {
		return killReward;
	}

	public void setKillReward(List<Goods> killReward) {
		this.killReward = killReward;
	}

	public long getHurt() {
		return hurt;
	}

	public void setHurt(long hurt) {
		this.hurt = hurt;
	}

	public List<Goods> getHurtReward() {
		return hurtReward;
	}

	public void setHurtReward(List<Goods> hurtReward) {
		this.hurtReward = hurtReward;
	}
}
