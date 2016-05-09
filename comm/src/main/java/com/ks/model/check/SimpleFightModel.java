package com.ks.model.check;

import com.ks.model.soul.Soul;

/**
 * 简单战斗模型对象
 * @author hanjie.l
 *
 */
public class SimpleFightModel{
    /**战魂唯一id*/
    private long userSoulId;
    /**攻击*/
    private int attack;
    /**防御*/
    private int defense;
    /**当前血量*/
    private int hp;
    /**回复力*/
    private int reply;
    /**战魂*/
    private Soul soul;
    
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
	public Soul getSoul() {
		return soul;
	}
	public void setSoul(Soul soul) {
		this.soul = soul;
	}
}