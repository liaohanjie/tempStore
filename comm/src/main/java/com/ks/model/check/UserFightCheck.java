package com.ks.model.check;
/**
 * 玩家战斗检查
 * @author admin
 *
 */
public class UserFightCheck {
	
	/**
	 * 玩家id
	 */
	private int  userId;
	
	/**
	 * 最近一次检查通过战斗类型
	 */
	private BattleType battleType;
	
	/**
	 * 检查是否通过
	 */
	private boolean pass;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public BattleType getBattleType() {
		return battleType;
	}

	public void setBattleType(BattleType battleType) {
		this.battleType = battleType;
	}

	public boolean isPass() {
		return pass;
	}

	public void setPass(boolean pass) {
		this.pass = pass;
	}
}
