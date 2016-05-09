package com.ks.model.fight;

import java.io.Serializable;
import java.util.List;
/**
 * 战斗主动技能
 * @author ks
 */
public class FightSkill implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**主动技ID*/
	private int skillId;
	/**技能效果*/
	private List<FightSkillEffect> effects;
	
	public int getSkillId() {
		return skillId;
	}
	public void setSkillId(int skillId) {
		this.skillId = skillId;
	}
	public List<FightSkillEffect> getEffects() {
		return effects;
	}
	public void setEffects(List<FightSkillEffect> effects) {
		this.effects = effects;
	}
}
