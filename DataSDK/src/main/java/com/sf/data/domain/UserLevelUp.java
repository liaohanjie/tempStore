package com.sf.data.domain;

import java.io.Serializable;
import java.util.Date;
/**
 * 用户升级
 * @author living.li
 * @date 2014年7月8日
 */
public class UserLevelUp implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int gameId;
	
	private String serverId;
	
	private int partner;
	
	private int roleId;
	
	private int level;
	
	private Date levelUpTime;
	
	private Date loginTime;
	
	
	public static UserLevelUp create(int partner,int roleId,int level,Date leveUpTime,Date loginTime){
		UserLevelUp up=new UserLevelUp();
		up.setLevel(level);
		up.setPartner(partner);
		up.setRoleId(roleId);
		up.setLevelUpTime(leveUpTime);
		up.setLoginTime(loginTime);
		return up;
	}

	public String getServerId() {
		return serverId;
	}

	public int getGameId() {
		return gameId;
	}

	public void setGameId(int gameId) {
		this.gameId = gameId;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public void setServerId(String serverId) {
		this.serverId = serverId;
	}

	public int getPartner() {
		return partner;
	}
	

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public void setPartner(int partner) {
		this.partner = partner;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public Date getLevelUpTime() {
		return levelUpTime;
	}

	public void setLevelUpTime(Date levelUpTime) {
		this.levelUpTime = levelUpTime;
	}
	
	
}
