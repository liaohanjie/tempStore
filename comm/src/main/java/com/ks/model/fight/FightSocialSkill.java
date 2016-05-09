package com.ks.model.fight;

import java.io.Serializable;
/**
 * 战斗合作技能
 * @author ks
 */
public class FightSocialSkill implements Serializable {

	private static final long serialVersionUID = 1L;
	/**合作战魂编号*/
	private int soulId;
	/**技能*/
	private FightSkill skill;
	public int getSoulId() {
		return soulId;
	}
	public void setSoulId(int soulId) {
		this.soulId = soulId;
	}
	public FightSkill getSkill() {
		return skill;
	}
	public void setSkill(FightSkill skill) {
		this.skill = skill;
	}
	
}
