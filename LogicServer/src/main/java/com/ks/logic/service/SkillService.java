package com.ks.logic.service;

import java.util.List;

import com.ks.model.skill.ActiveSkill;
import com.ks.model.skill.ActiveSkillEffect;
import com.ks.model.skill.CapSkill;
import com.ks.model.skill.CapSkillEffect;
import com.ks.model.skill.SocialSkill;
import com.ks.model.skill.SocialSkillRule;
import com.ks.model.skill.SocialSkillEffect;

public interface SkillService {
	/**
	 * 查询主动技能
	 * @return
	 */
	public List<ActiveSkill> queryActiveSkills();
	/**
	 * 主动技能效果
	 * @return
	 */
	public List<ActiveSkillEffect> queryActiveSkillEffects();
	/**
	 * 队长技能效果
	 * @return
	 */
	public List<CapSkill> queryCapSkills();
	/**
	 *队长技能效果
	 * @return
	 */
	public List<CapSkillEffect> queryCapSkillEffect();
	/**
	 * 合作技能
	 * @return
	 */
	public List<SocialSkill> querySocialSkill();
	/**
	 * 
	 * @return
	 */
	public List<SocialSkillEffect> querySoicalSkillEffects();
	
	public List<SocialSkillRule> querySocialSkillRule();
}
