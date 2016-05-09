package com.ks.protocol.vo.fight;

import java.util.List;

import com.ks.protocol.Message;

public abstract class AttackBoutVO extends Message {

	private static final long serialVersionUID = 1L;
	/**攻击回合*/
	private List<AttackRoundVO> rounds;
	/**buff影响*/
	private List<BuffImpactVO> buffImpacts;
	/**攻击掉落*/
	private List<AttackDropVO> drops;
	public List<AttackRoundVO> getRounds() {
		return rounds;
	}
	public void setRounds(List<AttackRoundVO> rounds) {
		this.rounds = rounds;
	}
	public List<AttackDropVO> getDrops() {
		return drops;
	}
	public void setDrops(List<AttackDropVO> drops) {
		this.drops = drops;
	}
	public List<BuffImpactVO> getBuffImpacts() {
		return buffImpacts;
	}
	public void setBuffImpacts(List<BuffImpactVO> buffImpacts) {
		this.buffImpacts = buffImpacts;
	}
	
}
