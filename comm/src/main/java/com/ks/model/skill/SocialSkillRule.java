package com.ks.model.skill;

import java.io.Serializable;
/**
 * 合作技能
 * @author living.li
 * @date 2014年7月1日
 */
public class SocialSkillRule implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**主动战魂ID*/
	private int soulId;
	/**合做战魂ID*/
	private int joinSoulId;
	/**合作技能ID*/
	private int skillId;
	public int getSoulId() {
		return soulId;
	}
	public void setSoulId(int soulId) {
		this.soulId = soulId;
	}
	public int getJoinSoulId() {
		return joinSoulId;
	}
	public void setJoinSoulId(int joinSoulId) {
		this.joinSoulId = joinSoulId;
	}
	public int getSkillId() {
		return skillId;
	}
	public void setSkillId(int skillId) {
		this.skillId = skillId;
	}
	
	
	

}
