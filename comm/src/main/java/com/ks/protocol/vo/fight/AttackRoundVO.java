package com.ks.protocol.vo.fight;

import java.util.List;

import com.ks.protocol.Message;

public class AttackRoundVO extends Message {

	private static final long serialVersionUID = 1L;
	/**攻击者战斗编号*/
	private int fightId;
	/**技能编号*/
	private int skillId;
	/**合作战斗编号*/
	private int socialFightId;
	/**伤害次数*/
	private int hit;
	/**攻击*/
	private List<AttackVO> attacks;
	public int getFightId() {
		return fightId;
	}
	public void setFightId(int fightId) {
		this.fightId = fightId;
	}
	public List<AttackVO> getAttacks() {
		return attacks;
	}
	public void setAttacks(List<AttackVO> attacks) {
		this.attacks = attacks;
	}
	public int getSkillId() {
		return skillId;
	}
	public void setSkillId(int skillId) {
		this.skillId = skillId;
	}
	public int getHit() {
		return hit;
	}
	public void setHit(int hit) {
		this.hit = hit;
	}
	public int getSocialFightId() {
		return socialFightId;
	}
	public void setSocialFightId(int socialFightId) {
		this.socialFightId = socialFightId;
	}
	
}
