package com.ks.protocol.vo.fight;

import java.util.List;

import com.ks.protocol.Message;

public class FightVO extends Message {

	private static final long serialVersionUID = 1L;
	
	/**攻击方*/
	private List<FightModelVO> attackers;
	/**防守方*/
	private List<FightModelVO> defenders;
	/**攻击轮*/
	private List<AttackBoutVO> bouts;
	/**攻击者胜利*/
	private boolean attWin;
	
	public List<FightModelVO> getAttackers() {
		return attackers;
	}
	public void setAttackers(List<FightModelVO> attackers) {
		this.attackers = attackers;
	}
	public List<FightModelVO> getDefenders() {
		return defenders;
	}
	public void setDefenders(List<FightModelVO> defenders) {
		this.defenders = defenders;
	}
	public boolean isAttWin() {
		return attWin;
	}
	public void setAttWin(boolean attWin) {
		this.attWin = attWin;
	}
	public List<AttackBoutVO> getBouts() {
		return bouts;
	}
	public void setBouts(List<AttackBoutVO> bouts) {
		this.bouts = bouts;
	}
	
}
