package com.ks.logic.service;

import java.util.List;

import com.ks.model.skill.CapSkillEffect;
import com.ks.model.skill.SkillStrike;
import com.ks.model.soul.Soul;
import com.ks.model.soul.SoulEvolution;

/**
 * 战魂服务
 * @author ks
 */
public interface SoulService {
	/**
	 * 查询所有战魂
	 * @return 所有战魂
	 */
	List<Soul> queryAllSoul();
	/**
	 * 查询所有进化配方
	 * @return 进化配方
	 */
	
	List<SoulEvolution> querySoulEvolution();
	/**
	 * 查询队长技能效果
	 * @return 所有队长技能效果
	 */
	List<CapSkillEffect> queryCapSkillEffects();
	/**
	 * 查询技能触发
	 * @return 技能触发
	 */
	List<SkillStrike> querySkillStrike();
}
