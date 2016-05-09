package com.ks.logic.service.impl;

import java.util.List;

import com.ks.logic.service.BaseService;
import com.ks.logic.service.SoulService;
import com.ks.model.skill.CapSkillEffect;
import com.ks.model.skill.SkillStrike;
import com.ks.model.soul.Soul;
import com.ks.model.soul.SoulEvolution;

public class SoulServiceImpl extends BaseService implements SoulService {

	@Override
	public List<Soul> queryAllSoul() {
		return soulCfgDAO.findAllSoul();
	}

	@Override
	public List<SoulEvolution> querySoulEvolution() {
		return soulCfgDAO.querySoulEvolution();
	}

	@Override
	public List<CapSkillEffect> queryCapSkillEffects() {
		return soulCfgDAO.queryCapSkillEffects();
	}

	@Override
	public List<SkillStrike> querySkillStrike() {
		return soulCfgDAO.querySkillStrike();
	}

}
