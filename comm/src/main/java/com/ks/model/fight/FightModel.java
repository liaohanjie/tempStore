package com.ks.model.fight;

import java.io.Serializable;
import java.util.List;

/**
 * 战斗模型
 * 
 * @author ks
 */
public class FightModel implements Serializable {

	private static final long serialVersionUID = 1L;
	/** 1火 2土 3气 4水 5光6暗 */
	private static final double[][] RESTRAINT = new double[][] {
			{ 1, 1.5, 1, 0.5, 1, 1 }, { 0.5, 1, 1.5, 1, 1, 1 },
			{ 1, 0.5, 1, 1.5, 1, 1 }, { 1.5, 1, 0.5, 1, 1, 1 },
			{ 1, 1, 1, 1, 1, 1.5 }, { 1, 1, 1, 1, 1.5, 1 } };
	
	public static final int ELE_火 = 1;
	public static final int ELE_土 = 2;
	public static final int ELE_气 = 3;
	public static final int ELE_水 = 4;
	public static final int ELE_光 = 5;
	public static final int ELE_暗 = 6;
	public static final int ELE_无 = 7;
	
	/** 战斗编号 */
	private int fightId;
	/**所在位置*/
	private byte pos;
	/** 玩家编号 */
	private int userId;
	/**用户战魂编号*/
	private long userSoulId;
	/** 战魂编号 */
	private int soulId;
	/** 攻击力 */
	private int att;
	/** 防御力 */
	private int def;
	/**暴击率*/
	private double critRate=0.15;
	/** 最大血量 */
	private int maxHp;
	/** 当前血量 */
	private int hp;
	/** 恢复 */
	private int rep;
	/** 攻击次数 */
	private int hit;
	/** 怒气 */
	private int anger;
	/** 属性 */
	private int soulEle;
	/** 主动技能 */
	private FightSkill skill;
	/** 战斗buff */
	private List<FightBuff> buffs;
	/**等级*/
	private int level;
	/**
	 * 获取属性相克
	 * 
	 * @param attEle
	 *            攻击方属性
	 * @param defEle
	 *            防守方属性
	 * @return 攻击百分比
	 */
	public static final double getRestraint(int attEle, int defEle) {
		if(attEle==ELE_无 || defEle ==ELE_无){
			return 1;
		}
		return RESTRAINT[attEle - 1][defEle - 1];
	}
	public int getFightId() {
		return fightId;
	}
	public void setFightId(int fightId) {
		this.fightId = fightId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getSoulId() {
		return soulId;
	}
	public void setSoulId(int soulId) {
		this.soulId = soulId;
	}
	public int getAtt() {
		return att;
	}
	public void setAtt(int att) {
		this.att = att;
	}
	public int getDef() {
		return def;
	}
	public void setDef(int def) {
		this.def = def;
	}
	public int getHp() {
		return hp;
	}
	public void setHp(int hp) {
		this.hp = hp;
	}
	public int getAnger() {
		return anger;
	}
	public void setAnger(int anger) {
		this.anger = anger;
	}
	public int getMaxHp() {
		return maxHp;
	}
	public void setMaxHp(int maxHp) {
		this.maxHp = maxHp;
	}
	public int getRep() {
		return rep;
	}
	public void setRep(int rep) {
		this.rep = rep;
	}
	public int getHit() {
		return hit;
	}
	public void setHit(int hit) {
		this.hit = hit;
	}
	public FightSkill getSkill() {
		return skill;
	}
	public void setSkill(FightSkill skill) {
		this.skill = skill;
	}
	public List<FightBuff> getBuffs() {
		return buffs;
	}
	public void setBuffs(List<FightBuff> buffs) {
		this.buffs = buffs;
	}
	public long getUserSoulId() {
		return userSoulId;
	}
	public void setUserSoulId(long userSoulId) {
		this.userSoulId = userSoulId;
	}
	public int getSoulEle() {
		return soulEle;
	}
	public void setSoulEle(int soulEle) {
		this.soulEle = soulEle;
	}
	public double getCritRate() {
		return critRate;
	}
	public void setCritRate(double critRate) {
		this.critRate = critRate;
	}
	public byte getPos() {
		return pos;
	}
	public void setPos(byte pos) {
		this.pos = pos;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	
}
