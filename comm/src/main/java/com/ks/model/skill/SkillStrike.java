package com.ks.model.skill;

import java.io.Serializable;
/**
 * 技能触发
 * @author ks
 */
public class SkillStrike implements Serializable {

	private static final long serialVersionUID = 1L;
	/**技能编号*/
	private int skillId;
	/**触发*/
	private int strike;
	/**触发值*/
	private int strikeValue;
	public int getSkillId() {
		return skillId;
	}
	public void setSkillId(int skillId) {
		this.skillId = skillId;
	}
	public int getStrike() {
		return strike;
	}
	public void setStrike(int strike) {
		this.strike = strike;
	}
	public int getStrikeValue() {
		return strikeValue;
	}
	public void setStrikeValue(int strikeValue) {
		this.strikeValue = strikeValue;
	}
	
}
