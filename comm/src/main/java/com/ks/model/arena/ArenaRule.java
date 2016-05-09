package com.ks.model.arena;

import java.io.Serializable;
/**
 * 竞技场规则
 * @author ks
 */
public class ArenaRule implements Serializable {

	private static final long serialVersionUID = 1L;
	/**等级*/
	private int level;
	/**是否可降级*/
	private boolean demote;
	/**胜利场数*/
	private int win;
	/**奖励类型*/
	private int awardType;
	/**奖励编号*/
	private int awardId;
	/**数量*/
	private int num;
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public boolean isDemote() {
		return demote;
	}
	public void setDemote(boolean demote) {
		this.demote = demote;
	}
	public int getWin() {
		return win;
	}
	public void setWin(int win) {
		this.win = win;
	}
	public int getAwardType() {
		return awardType;
	}
	public void setAwardType(int awardType) {
		this.awardType = awardType;
	}
	public int getAwardId() {
		return awardId;
	}
	public void setAwardId(int awardId) {
		this.awardId = awardId;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	
}
