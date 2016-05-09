/**
 * 
 */
package com.ks.model.equipment;

import java.io.Serializable;

/**
 * @author living.li
 * @date  2014年12月29日 下午5:46:27
 *
 *
 */
public class EquipmentSkill implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static  final int SKILL_TYPE_进攻型=1;
	public static  final int SKILL_TYPE_防御型=2;
	/**道具ID*/
	private int propId;
	/**主动技能ID*/
	private int activeSkillId;
	/**技能等级*/
	private int skillLevel;
	/**技能类型*/
	private int skillType;
	/**消耗的金币*/
	private int gold;
	@Override
	public String toString() {
		return "EquipmentSkill [propId=" + propId + ", activeSkillId="
				+ activeSkillId + ", skillType=" + skillType + ", skillLevel" + skillLevel +"]";
	}
	public int getPropId() {
		return propId;
	}
	public void setPropId(int propId) {
		this.propId = propId;
	}
	public int getActiveSkillId() {
		return activeSkillId;
	}
	public void setActiveSkillId(int activeSkillId) {
		this.activeSkillId = activeSkillId;
	}
	public int getSkillType() {
		return skillType;
	}
	public void setSkillType(int skillType) {
		this.skillType = skillType;
	}
	public int getGold() {
		return gold;
	}
	public void setGold(int gold) {
		this.gold = gold;
	}
	public int getSkillLevel() {
		return skillLevel;
	}
	public void setSkillLevel(int skillLevel) {
		this.skillLevel = skillLevel;
	}
}
