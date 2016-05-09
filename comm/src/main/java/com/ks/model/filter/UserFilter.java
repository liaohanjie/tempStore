package com.ks.model.filter;

public class UserFilter extends Filter {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** 用户id */
	private int userId;
	/** 合作方 */
	private int partner;
	/** 用户名 */
	private String username;
	/** 玩家名 */
	private String playerName;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getPartner() {
		return partner;
	}

	public void setPartner(int partner) {
		this.partner = partner;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

}
