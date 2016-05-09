package com.ks.protocol.vo.alliance;

import com.ks.model.alliance.constant.RoleType;
import com.ks.protocol.Message;

/**
 * 工会成员对象
 * @author admin
 *
 */
public class AllianceMemberVO extends Message{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1246383535006481829L;

	/**
	 * 在工会中的角色{@link RoleType}
	 */
	private int role;
	
	/**
	 * 玩家id
	 */
	private int userId;
	
	/**
	 * 玩家名
	 */
	private String playerName;

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

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
}
