package com.ks.model.swaparena;

import com.ks.model.user.User;

/**
 * 交换排名竞技赛日志记录
 * @author hanjie.l
 *
 */
public class SwapArenaFightLog {
	
	/**
	 *主键id
	 */
	private int id;
	
	/**
	 * 插入时间
	 */
	private long updateTime;
	
	/**
	 * 对攻击者来说是赢还是输
	 */
	private boolean win;
	
	/**
	 * 攻击者id
	 */
	private int attackId;
	
	/**
	 * 攻击者姓名
	 */
	private String attackName;
	
	/**
	 * 攻击者队长战魂
	 */
	private int attackSoulId;
	
	/**
	 * 攻击前名次
	 */
	private int attackOldRank;
	
	/**
	 * 攻击后名次
	 */
	private int attackNewRank;
	
	//============攻守分割线=================
	
	/**
	 * 防守者id
	 */
	private int defendId;
	
	/**
	 * 防守者姓名
	 */
	private String defendName;
	
	/**
	 * 防守者队长战魂id
	 */
	private int defendSoulId;
	
	/**
	 * 防守者名次
	 */
	private int defendOldRank;
	
	/**
	 * 防守者名次
	 */
	private int defendNewRank;
	
	public void init(User attacker, User defender){
		this.attackId = attacker.getUserId();
		this.attackName = attacker.getPlayerName();
		this.defendId = defender.getUserId();
		this.defendName = defender.getPlayerName();
	}

	public boolean isWin() {
		return win;
	}
	public void setWin(boolean win) {
		this.win = win;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
	}

	public int getAttackId() {
		return attackId;
	}

	public void setAttackId(int attackId) {
		this.attackId = attackId;
	}

	public String getAttackName() {
		return attackName;
	}

	public void setAttackName(String attackName) {
		this.attackName = attackName;
	}

	public int getAttackOldRank() {
		return attackOldRank;
	}

	public void setAttackOldRank(int attackOldRank) {
		this.attackOldRank = attackOldRank;
	}

	public int getAttackNewRank() {
		return attackNewRank;
	}

	public void setAttackNewRank(int attackNewRank) {
		this.attackNewRank = attackNewRank;
	}

	public int getDefendId() {
		return defendId;
	}

	public void setDefendId(int defendId) {
		this.defendId = defendId;
	}

	public String getDefendName() {
		return defendName;
	}

	public void setDefendName(String defendName) {
		this.defendName = defendName;
	}

	public int getDefendOldRank() {
		return defendOldRank;
	}

	public void setDefendOldRank(int defendOldRank) {
		this.defendOldRank = defendOldRank;
	}

	public int getDefendNewRank() {
		return defendNewRank;
	}

	public void setDefendNewRank(int defendNewRank) {
		this.defendNewRank = defendNewRank;
	}

	public int getAttackSoulId() {
		return attackSoulId;
	}

	public void setAttackSoulId(int attackSoulId) {
		this.attackSoulId = attackSoulId;
	}

	public int getDefendSoulId() {
		return defendSoulId;
	}

	public void setDefendSoulId(int defendSoulId) {
		this.defendSoulId = defendSoulId;
	}
}
