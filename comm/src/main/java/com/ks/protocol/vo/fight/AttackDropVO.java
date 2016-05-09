package com.ks.protocol.vo.fight;

import com.ks.protocol.Message;
/**
 * 攻击掉落
 * @author ks
 */
public class AttackDropVO extends Message {

	private static final long serialVersionUID = 1L;
	/**编号*/
	private int fightId;
	/**数量*/
	private int num;
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public int getFightId() {
		return fightId;
	}
	public void setFightId(int fightId) {
		this.fightId = fightId;
	}
	
}
