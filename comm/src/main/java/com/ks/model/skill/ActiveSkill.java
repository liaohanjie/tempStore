package com.ks.model.skill;

import java.io.Serializable;
import java.util.List;
/**
 * 主动技能
 * @author living.li
 * @date   2014年5月15日
 */
public class ActiveSkill extends AbstractSkill implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	/**最高等级*/
	private int maxLevel;
	/**技能效果*/
	private List<ActiveSkillEffect> effects;

	public int getMaxLevel() {
		return maxLevel;
	}
	public void setMaxLevel(int maxLevel) {
		this.maxLevel = maxLevel;
	}
	public List<ActiveSkillEffect> getEffects() {
		return effects;
	}
	public void setEffects(List<ActiveSkillEffect> effects) {
		this.effects = effects;
	}

	
		
}
