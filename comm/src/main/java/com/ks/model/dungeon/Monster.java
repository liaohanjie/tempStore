package com.ks.model.dungeon;

import java.io.Serializable;
/**
 * 怪物
 * @author ks
 */
public class Monster implements Serializable {

	private static final long serialVersionUID = 1L;
	
	//monster_id	pic	name	ele	hp	atk	def	hit	skill_id	exp	drop_id


	/**怪物ID*/
	private int monsterId;	
	/**怪物名称*/
	private String name;
	/**属性*/
	private int ele;
	/**血量*/
	private int hp;	
	/**经验*/
	private int exp;
	/**进攻*/
	private int atk;
	/**防御*/
	private int def;
	/**伤害次数*/
	private int hit;
	/**drop_id*/
	private int dropId;
	/**战魂id*/
	private int soulId;
	/**跑马灯(0:不显示， 1:显示)*/
	private int marquee;
	
	public int getSoulId() {
		return soulId;
	}
	public void setSoulId(int soulId) {
		this.soulId = soulId;
	}
	public int getMonsterId() {
		return monsterId;
	}
	public void setMonsterId(int monsterId) {
		this.monsterId = monsterId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getEle() {
		return ele;
	}
	public void setEle(int ele) {
		this.ele = ele;
	}
	public int getHp() {
		return hp;
	}
	public void setHp(int hp) {
		this.hp = hp;
	}
	public int getExp() {
		return exp;
	}
	public void setExp(int exp) {
		this.exp = exp;
	}
	public int getAtk() {
		return atk;
	}
	public void setAtk(int atk) {
		this.atk = atk;
	}
	public int getDef() {
		return def;
	}
	public void setDef(int def) {
		this.def = def;
	}
	public int getHit() {
		return hit;
	}
	public void setHit(int hit) {
		this.hit = hit;
	}
	
	public int getDropId() {
		return dropId;
	}
	public void setDropId(int dropId) {
		this.dropId = dropId;
	}
	public int getMarquee() {
		return marquee;
	}
	public void setMarquee(int marquee) {
		this.marquee = marquee;
	}
}
