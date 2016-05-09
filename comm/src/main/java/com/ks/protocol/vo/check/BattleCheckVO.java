package com.ks.protocol.vo.check;

import com.ks.protocol.Message;

public class BattleCheckVO extends Message{
    /**
	 * 
	 */
	private static final long serialVersionUID = -7784596662675055947L;
	/**
	 * 仅仅是为了解析没有其他含义
	 */
	private boolean bit;
	/**战魂唯一id*/
    private long userSoulId;
    /**机器人战魂*/
    private int soulId;
    /**机器人战魂等级*/
    private int level;
    /**攻击*/
    private int attack;
    /**防御*/
    private int defense;
    /**当前血量*/
    private int hp;
    /**回复力*/
    private int reply;
    /**目标怪物id*/
    private int monsterId;
    /**0为普通攻击，非0为技能攻击*/
    private int skillId;
    /**是否暴击*/
    private boolean crit;
    /**伤害值*/
    private int damageValue;
    /**是否为本人的战魂*/
    private boolean mysoul;
    
	public boolean isMysoul() {
		return mysoul;
	}
	public void setMysoul(boolean mysoul) {
		this.mysoul = mysoul;
	}
	public int getSoulId() {
		return soulId;
	}
	public void setSoulId(int soulId) {
		this.soulId = soulId;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public boolean isBit() {
		return bit;
	}
	public void setBit(boolean bit) {
		this.bit = bit;
	}
	public long getUserSoulId() {
		return userSoulId;
	}
	public void setUserSoulId(long userSoulId) {
		this.userSoulId = userSoulId;
	}
	public int getAttack() {
		return attack;
	}
	public void setAttack(int attack) {
		this.attack = attack;
	}
	public int getDefense() {
		return defense;
	}
	public void setDefense(int defense) {
		this.defense = defense;
	}
	public int getHp() {
		return hp;
	}
	public void setHp(int hp) {
		this.hp = hp;
	}
	public int getReply() {
		return reply;
	}
	public void setReply(int reply) {
		this.reply = reply;
	}
	public int getMonsterId() {
		return monsterId;
	}
	public void setMonsterId(int monsterId) {
		this.monsterId = monsterId;
	}
	public int getSkillId() {
		return skillId;
	}
	public void setSkillId(int skillId) {
		this.skillId = skillId;
	}
	public boolean isCrit() {
		return crit;
	}
	public void setCrit(boolean crit) {
		this.crit = crit;
	}
	public int getDamageValue() {
		return damageValue;
	}
	public void setDamageValue(int damageValue) {
		this.damageValue = damageValue;
	}
}
    
