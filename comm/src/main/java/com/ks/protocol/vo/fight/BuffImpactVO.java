package com.ks.protocol.vo.fight;

import com.ks.protocol.Message;
/**
 * buff影响
 * @author ks
 */
public abstract class BuffImpactVO extends Message {

	private static final long serialVersionUID = 1L;
	
	/**战斗编号*/
	private int fightId;
	/**buff编号*/
	private int buffId;
	/**值*/
	private int val;
	/**剩余血量*/
	private int hp;
	public int getFightId() {
		return fightId;
	}
	public void setFightId(int fightId) {
		this.fightId = fightId;
	}
	public int getBuffId() {
		return buffId;
	}
	public void setBuffId(int buffId) {
		this.buffId = buffId;
	}
	public int getVal() {
		return val;
	}
	public void setVal(int val) {
		this.val = val;
	}
	public int getHp() {
		return hp;
	}
	public void setHp(int hp) {
		this.hp = hp;
	}
	
}
