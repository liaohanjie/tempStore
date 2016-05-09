package com.ks.protocol.vo.fight;

import com.ks.model.fight.FightModel;
import com.ks.protocol.Message;

public class FightModelVO extends Message {

	private static final long serialVersionUID = 1L;
	
	/** 战斗编号 */
	private int fightId;
	/** 战魂编号 */
	private int soulId;
	/**位置*/
	private int pos;
	/** 攻击力 */
	private int att;
	/** 防御力 */
	private int def;
	/**恢复*/
	private int rep;
	/** 最大血量 */
	private int maxHp;
	
	public void init(FightModel o){
		this.fightId = o.getFightId();
		this.soulId = o.getSoulId();
		this.att = o.getAtt();
		this.def = o.getDef();
		this.maxHp = o.getMaxHp();
		this.rep = o.getRep();
		this.pos = o.getPos();
	}
	
	public int getFightId() {
		return fightId;
	}
	public void setFightId(int fightId) {
		this.fightId = fightId;
	}
	public int getSoulId() {
		return soulId;
	}
	public void setSoulId(int soulId) {
		this.soulId = soulId;
	}
	public int getAtt() {
		return att;
	}
	public void setAtt(int att) {
		this.att = att;
	}
	public int getDef() {
		return def;
	}
	public void setDef(int def) {
		this.def = def;
	}
	public int getMaxHp() {
		return maxHp;
	}
	public void setMaxHp(int maxHp) {
		this.maxHp = maxHp;
	}
	public int getRep() {
		return rep;
	}
	public void setRep(int rep) {
		this.rep = rep;
	}

	public int getPos() {
		return pos;
	}

	public void setPos(int pos) {
		this.pos = pos;
	}
	
}
