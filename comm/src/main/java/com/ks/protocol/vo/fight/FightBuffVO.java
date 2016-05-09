package com.ks.protocol.vo.fight;

import com.ks.model.fight.FightBuff;
import com.ks.protocol.Message;

public class FightBuffVO extends Message {

	private static final long serialVersionUID = 1L;
	
	/**buff编号*/
	private int buffId;
	/**效果值*/
	private int addPoint;
	/**效果比*/
	private double addPercent;	
	/**回合*/
	private int round;
	
	public void init(FightBuff o){
		this.buffId = o.getBuffId();
		this.addPoint = o.getAddPoint();
		this.addPercent = o.getAddPercent();
		this.round = o.getRound();
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
