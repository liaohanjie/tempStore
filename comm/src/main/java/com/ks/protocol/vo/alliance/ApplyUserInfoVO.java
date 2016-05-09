package com.ks.protocol.vo.alliance;

import com.ks.protocol.Message;

/**
 * 申请人信息
 * @author admin
 *
 */
public class ApplyUserInfoVO extends Message{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2898116368237631087L;

	/**
	 * 玩家id
	 */
	private int userId;
	
	/**
	 * 玩家名
	 */
	private String playerName;
	
	/**
	 * 玩家等级
	 */
	private int playerLevel;
	
	/**
	 * 申请时间
	 */
	private  long applyTime;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public int getPlayerLevel() {
		return playerLevel;
	}

	public void setPlayerLevel(int playerLevel) {
		this.playerLevel = playerLevel;
	}

	public long getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(long applyTime) {
		this.applyTime = applyTime;
	}
}
