package com.ks.model.goods;

import java.io.Serializable;
/**
 * 
 * @author ks
 */
public class PropEffect implements Serializable {

	public static final int EFFECT_TYPE_加血 = 1;
	public static final int EFFECT_TYPE_攻击 = 2;
	public static final int EFFECT_TYPE_防御 = 3;
	public static final int EFFECT_TYPE_解除虚弱 = 4;
	public static final int EFFECT_TYPE_解除疾病 = 5;
	public static final int EFFECT_TYPE_解除负伤 = 6;
	public static final int EFFECT_TYPE_解除麻痹 = 7;
	public static final int EFFECT_TYPE_解除诅咒 = 8;
	public static final int EFFECT_TYPE_解除中毒 = 9;
	public static final int EFFECT_TYPE_减少伤害 = 10;
	public static final int EFFECT_TYPE_复活 = 11;
	//public static final int EFFECT_TYPE_回复力 = 12;
	
	private static final long serialVersionUID = 1L;
	/**道具编号*/
	private int propId;
	/**效果类型*/
	private int effectType;
	
	private int target;
	/**目标属性*/
	private int targetEle;
	/**效果值*/
	private int addPoint;
	/**效果百分比*/
	private double addPercent;	
	/**持续回合数*/
	private int round;
	
	public int getPropId() {
		return propId;
	}
	public void setPropId(int propId) {
		this.propId = propId;
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
	
	
}
