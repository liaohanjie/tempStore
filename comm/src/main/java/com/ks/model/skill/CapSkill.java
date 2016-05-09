package com.ks.model.skill;

import java.io.Serializable;
import java.util.List;
/**
 * 队长技能
 * @author ks
 */
public class CapSkill extends AbstractSkill implements Serializable {

	private static final long serialVersionUID = 1L;
	/**队长技能效果*/
	private List<CapSkillEffect> effects;
	/**该技能触需要 的成员属性*/
	private List<Integer> needEle;
	
	public List<Integer> getNeedEle() {
		return needEle;
	}
	public void setNeedEle(List<Integer> needEle) {
		this.needEle = needEle;
	}
	public List<CapSkillEffect> getEffects() {
		return effects;
	}
	public void setEffects(List<CapSkillEffect> effects) {
		this.effects = effects;
	}
	
}
