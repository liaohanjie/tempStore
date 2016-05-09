package com.ks.protocol.vo.fight;

import com.ks.protocol.Message;
/**
 * 攻击
 * @author ks
 */
public class AttackVO extends Message {

	private static final long serialVersionUID = 1L;
	
	/**被攻击者战斗编号*/
	private int fightId;
	/**伤害类型*/
	private int attackType;
	/**伤害*/
	private int hurt;
	/**剩余HP*/
	private int hp;
	/**掉落数量*/
	private int dropNum;
	/**战斗中所产生的buff*/
	private FightBuffVO buff;
	
	
	
	public int getFightId() {
		return fightId;
	}
	public void setFightId(int fightId) {
		this.fightId = fightId;
	}
	public int getAttackType() {
		return attackType;
	}
	public void setAttackType(int attackType) {
		this.attackType = attackType;
	}
	public void setHp(int hp) {
		this.hp = hp;
	}
	public int getHp() {
		return hp;
	}
	public int getHurt() {
		return hurt;
	}
	public void setHurt(int hurt) {
		this.hurt = hurt;
	}
	public int getDropNum() {
		return dropNum;
	}
	public void setDropNum(int dropNum) {
		this.dropNum = dropNum;
	}
	public FightBuffVO getBuff() {
		return buff;
	}
	public void setBuff(FightBuffVO buff) {
		this.buff = buff;
	}
	
}
