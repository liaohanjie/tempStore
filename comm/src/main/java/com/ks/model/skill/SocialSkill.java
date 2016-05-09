package com.ks.model.skill;

import java.io.Serializable;
import java.util.List;
/**
 * 合作技能
 * @author living.li
 * @date   2014年5月15日
 */
public class SocialSkill extends AbstractSkill implements Serializable{



	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<SocialSkillEffect> effects;
	
	public List<SocialSkillEffect> getEffects() {
		return effects;
	}
	public void setEffects(List<SocialSkillEffect> effects) {
		this.effects = effects;
	}	
}

