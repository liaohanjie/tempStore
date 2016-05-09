package com.ks.model.check;
/**
 * 配置
 * @author hanjie.l
 *
 */
public class CheckConfig {
	
	/**
	 * 主键id
	 */
	private int id;
	
	/**
	 * 是否开启校验
	 */
	private boolean open;
	
	/**
	 * 攻击力浮动
	 */
	private float attRange;
	
	/**
	 *生命浮动
	 */
	private float hpRange;
	
	/**
	 * 防御浮动
	 */
	private float defRange;
	
	/**
	 * 恢复力浮动
	 */
	private float replyRange;
	
	/**
	 * 带技能伤害浮动
	 */
	private float skillDamageRange;
	
	/**
	 * 普通攻击伤害浮动
	 */
	private float noSkillDamageRange;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public float getAttRange() {
		return attRange;
	}

	public void setAttRange(float attRange) {
		this.attRange = attRange;
	}

	public float getHpRange() {
		return hpRange;
	}

	public void setHpRange(float hpRange) {
		this.hpRange = hpRange;
	}

	public float getDefRange() {
		return defRange;
	}

	public void setDefRange(float defRange) {
		this.defRange = defRange;
	}

	public float getReplyRange() {
		return replyRange;
	}

	public void setReplyRange(float replyRange) {
		this.replyRange = replyRange;
	}

	public float getSkillDamageRange() {
		return skillDamageRange;
	}

	public void setSkillDamageRange(float skillDamageRange) {
		this.skillDamageRange = skillDamageRange;
	}

	public float getNoSkillDamageRange() {
		return noSkillDamageRange;
	}

	public void setNoSkillDamageRange(float noSkillDamageRange) {
		this.noSkillDamageRange = noSkillDamageRange;
	}

}
