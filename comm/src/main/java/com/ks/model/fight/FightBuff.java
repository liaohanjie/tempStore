package com.ks.model.fight;

import java.io.Serializable;
/**
 * 战斗buff
 * @author ks
 */
public class FightBuff implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**buff编号*/
	private int buffId;
	/**效果值*/
	private int addPoint;
	/**效果比*/
	private double addPercent;	
	/**回合*/
	private int round;
	/**当前次数*/
	private int currCount;
	/**成功概率*/
	private double successRate;
	public FightBuff(int buffId, int addPoint, double addPercent,int round,double successRate) {
		this.buffId = buffId;
		this.addPoint = addPoint;
		this.addPercent = addPercent;
		this.round = round;
		this.successRate = successRate;
	}
	public int getBuffId() {
		return buffId;
	}
	public void setBuffId(int buffId) {
		this.buffId = buffId;
	}
	public int getRound() {
		return round;
	}
	public void setRound(int round) {
		this.round = round;
	}
	public int getCurrCount() {
		return currCount;
	}
	public void setCurrCount(int currCount) {
		this.currCount = currCount;
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
	public double getSuccessRate() {
		return successRate;
	}
	public void setSuccessRate(double successRate) {
		this.successRate = successRate;
	}
	
}
