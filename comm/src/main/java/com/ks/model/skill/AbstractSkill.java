package com.ks.model.skill;

public abstract class AbstractSkill {
	
	/**队长技能编号*/
	private int skillId;
	/**队长技能名称*/
	private String skillName;
	
	
	public int getSkillId() {
		return skillId;
	}
	public void setSkillId(int skillId) {
		this.skillId = skillId;
	}
	public String getSkillName() {
		return skillName;
	}
	public void setSkillName(String skillName) {
		this.skillName = skillName;
	}	
	
		

}
