package com.ks.model.skill;

import java.io.Serializable;
/**
 * 队长技能效果
 * @author ks
 */
public class CapSkillEffect implements Serializable {

	private static final long serialVersionUID = 1L;
	
	
	

	/**技能ID*/
	private int skillId;
	/**效果类型*/
	private int effectType;
	/**效果目标*//*
	private int target;	*/
	/**目标属性*/
	private int targetEle;	
	/**成功率*/
	private double successRate;
	/**效果值*/
	private int addPoint;
	/**效果比*/
	private double addPercent;	

	public int getSkillId() {
		return skillId;
	}
	public void setSkillId(int skillId) {
		this.skillId = skillId;
	}

	public int getEffectType() {
		return effectType;
	}
	public void setEffectType(int effectType) {
		this.effectType = effectType;
	}
	public int getTargetEle() {
		return targetEle;
	}
	public void setTargetEle(int targetEle) {
		this.targetEle = targetEle;
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
	
}

