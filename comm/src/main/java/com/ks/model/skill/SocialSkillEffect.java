package com.ks.model.skill;

import java.io.Serializable;

/**
 * 
 * @author living.li
 * @date   2014年5月15日
 */
public class SocialSkillEffect implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**技能ID*/
	private int skillId;
	/**效果类型*/
	private int effectType;
	/**技能等级*/
	private int level;
	/**目标*/
	private int target;	
	/**目标属性*/
	private int targetEle;	
	/**成功率*/
	private double successRate;
	/**效果值*/
	private int addPoint;
	/**效果比*/
	private double addPercent;	
	/**效果回合数*/
	private int round;
	/**伤害次数*/
	private int hit;
	
	
	
	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getSkillId() {
		return skillId;
	}

	public void setSkillId(int skillId) {
		this.skillId = skillId;
	}

	public int getTargetEle() {
		return targetEle;
	}

	public void setTargetEle(int targetEle) {
		this.targetEle = targetEle;
	}

	public int getEffectType() {
		return effectType;
	}
	public void setEffectType(int effectType) {
		this.effectType = effectType;
	}
	public int getTarget() {
		return target;
	}
	public void setTarget(int target) {
		this.target = target;
	}
	public double getSuccessRate() {
		return successRate;
	}
	public void setSuccessRate(double successRate) {
		this.successRate = successRate;
	}
	public int getAddPoint() {
		return addPoint;
	}
	public void setAddPoint(int addPoint) {
		this.addPoint = addPoint;
	}
	public double getAddPercent() {
		return addPercent;
	}
	public void setAddPercent(double addPercent) {
		this.addPercent = addPercent;
	}
	public int getRound() {
		return round;
	}
	public void setRound(int round) {
		this.round = round;
	}
	public int getHit() {
		return hit;
	}
	public void setHit(int hit) {
		this.hit = hit;
	}
	
	
}
