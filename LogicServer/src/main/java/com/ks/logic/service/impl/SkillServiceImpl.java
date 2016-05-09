package com.ks.logic.service.impl;

import java.util.List;

import com.ks.logic.service.BaseService;
import com.ks.logic.service.SkillService;
import com.ks.model.skill.ActiveSkill;
import com.ks.model.skill.ActiveSkillEffect;
import com.ks.model.skill.CapSkill;
import com.ks.model.skill.CapSkillEffect;
import com.ks.model.skill.SocialSkill;
import com.ks.model.skill.SocialSkillRule;
import com.ks.model.skill.SocialSkillEffect;

public class SkillServiceImpl extends BaseService implements SkillService {

	@Override
	public List<ActiveSkill> queryActiveSkills() {
		return skillDAO.queryActiveSkills();
	}

	@Override
	public List<ActiveSkillEffect> queryActiveSkillEffects() {
		return skillDAO.queryActiveSkillEffects();
	}

	@Override
	public List<CapSkill> queryCapSkills() {
		return skillDAO.queryCapSkills();
	}

	@Override
	public List<CapSkillEffect> queryCapSkillEffect() {
		return skillDAO.queryCapSkillEffect();
	}

	@Override
	public List<SocialSkill> querySocialSkill() {
		return skillDAO.querySocialSkill();
	}
	@Override
	public List<SocialSkillEffect> querySoicalSkillEffects() {
		return skillDAO.queryCapSkillEffects();
	}

	@Override
	public List<SocialSkillRule> querySocialSkillRule() {
		return skillDAO.querySocialSkillRule();
	}

}
