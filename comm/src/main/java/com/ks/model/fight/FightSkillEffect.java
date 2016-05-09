package com.ks.model.fight;

import java.io.Serializable;

import com.ks.model.skill.ActiveSkillEffect;
import com.ks.model.skill.SocialSkillEffect;
/**
 * 战斗主动技能效果
 * @author ks
 */
public class FightSkillEffect implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**效果类型*/
	private int effectType;
	/**效果目标*/
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
	
	public void init(ActiveSkillEffect o,int hit){
		this.effectType = o.getEffectType();
		this.target = o.getTarget();
		this.targetEle = o.getTargetEle();
		this.successRate = o.getSuccessRate();
		this.addPoint = o.getAddPoint();
		this.addPercent = o.getAddPercent();
		this.round = o.getRound();
		this.hit = hit;
	}
	
	public void init(SocialSkillEffect o){
		this.effectType = o.getEffectType();
		this.target = o.getTarget();
		this.targetEle = o.getTargetEle();
		this.successRate = o.getSuccessRate();
		this.addPoint = o.getAddPoint();
		this.addPercent = o.getAddPercent();
		this.round = o.getRound();
		this.hit = o.getHit();
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
