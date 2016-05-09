package com.ks.protocol.vo.user;

import com.ks.model.user.UserSoul;
import com.ks.protocol.Message;
/**
 * 用户战魂
 * @author ks
 */
public class UserSoulVO extends Message {

	private static final long serialVersionUID = 1L;
	/**编号*/
	private long id;
	/**战魂编号*/
	private int soulId;
	/**战魂状态*/
	private int soulSafe;
	/**等级*/
	private int level;
	/**经验*/
	private int exp;
	/**主动技能*/
	private int skillLv;
	/**性格*/
	private int soulType;
	
	public void init(UserSoul o){
		this.id = o.getId();
		this.soulId = o.getSoulId();
		this.soulSafe = o.getSoulSafe();
		this.level = o.getLevel();
		this.exp = o.getExp();
		this.skillLv = o.getSkillLv();
		this.soulType = o.getSoulType();
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public int getSoulId() {
		return soulId;
	}
	public void setSoulId(int soulId) {
		this.soulId = soulId;
	}
	public int getSoulSafe() {
		return soulSafe;
	}
	public void setSoulSafe(int soulSafe) {
		this.soulSafe = soulSafe;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getExp() {
		return exp;
	}
	public void setExp(int exp) {
		this.exp = exp;
	}
	public int getSkillLv() {
		return skillLv;
	}
	public void setSkillLv(int skillLv) {
		this.skillLv = skillLv;
	}
	public int getSoulType() {
		return soulType;
	}
	public void setSoulType(int soulType) {
		this.soulType = soulType;
	}

}
