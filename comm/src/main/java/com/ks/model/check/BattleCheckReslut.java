package com.ks.model.check;


public class BattleCheckReslut {
	
	/**战魂唯一id*/
    private long userSoulId;
    /**攻击*/
    private int attack;
    /**防御*/
    private int defense;
    /**当前血量*/
    private int hp;
    /**回复力*/
    private int reply;
    /**目标怪物id*/
    private int monsterId;
    /**0为普通攻击，非0为技能攻击*/
    private int skillId;
    /**是否暴击*/
    private boolean crit;
    /**伤害值*/
    private int damageValue;
    
    public static BattleCheckReslut valueOf(SimpleFightModel simpleFightModel, int monsterId, int skillId, boolean crit, int damageValue){
    	BattleCheckReslut battleCheckReslut = new BattleCheckReslut();
    	battleCheckReslut.setUserSoulId(simpleFightModel.getUserSoulId());
    	battleCheckReslut.setAttack(simpleFightModel.getAttack());
    	battleCheckReslut.setDefense(simpleFightModel.getDefense());
    	battleCheckReslut.setHp(simpleFightModel.getHp());
    	battleCheckReslut.setReply(simpleFightModel.getReply());
    	battleCheckReslut.setSkillId(skillId);
    	battleCheckReslut.setMonsterId(monsterId);
    	battleCheckReslut.setDamageValue(damageValue);
    	battleCheckReslut.setCrit(crit);
    	return battleCheckReslut;
    }
    
	public long getUserSoulId() {
		return userSoulId;
	}
	public void setUserSoulId(long userSoulId) {
		this.userSoulId = userSoulId;
	}
	public int getAttack() {
		return attack;
	}
	public void setAttack(int attack) {
		this.attack = attack;
	}
	public int getDefense() {
		return defense;
	}
	public void setDefense(int defense) {
		this.defense = defense;
	}
	public int getHp() {
		return hp;
	}
	public void setHp(int hp) {
		this.hp = hp;
	}
	public int getReply() {
		return reply;
	}
	public void setReply(int reply) {
		this.reply = reply;
	}
	public int getMonsterId() {
		return monsterId;
	}
	public void setMonsterId(int monsterId) {
		this.monsterId = monsterId;
	}
	public int getSkillId() {
		return skillId;
	}
	public void setSkillId(int skillId) {
		this.skillId = skillId;
	}
	public boolean isCrit() {
		return crit;
	}
	public void setCrit(boolean crit) {
		this.crit = crit;
	}
	public int getDamageValue() {
		return damageValue;
	}
	public void setDamageValue(int damageValue) {
		this.damageValue = damageValue;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[userSoulId]:").append(userSoulId).append("\n");
		builder.append("[attack]:").append(attack).append("\n");
		builder.append("[defense]:").append(defense).append("\n");
		builder.append("[hp]:").append(hp).append("\n");
		builder.append("[reply]:").append(reply).append("\n");
		builder.append("[monsterId]:").append(monsterId).append("\n");
		builder.append("[skillId]:").append(skillId).append("\n");
		builder.append("[crit]:").append(crit).append("\n");
		builder.append("[damageValue]:").append(damageValue).append("\n");
		return builder.toString();
	}
}
