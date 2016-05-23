package com.ks.account.test.dao;

import java.io.Serializable;
import java.util.Date;

public class AccountRole implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 服务器ID */
	private String serverId;
	/** 渠道 */
	private int partner;
	/** 用户名 */
	private String username;
	/** 角色ID */
	private int roleId;
	/** 角色名 */
	private String roleName;
	/** 游戏时长 */
	private int gameTime;
	/** 较色等级 */
	private int grade;
	/** 最后升极时间 */
	private Date lastUpgradeTime;
	/** 用户数性 */
	private int property;
	/** 注册IP */
	private String regIp;
	/** 注册时间 */
	private Date regTime;
	/** 上线时间 */
	private Date loginTime;
	/** 登出时间 */
	private Date logoutTime;
	/** 版本 */
	private int version;
	/** 今日游戏次数 */
	private int todayPlayCount;
	/** 今日游戏时长 */
	private int todayPlayTime;

	public String getServerId() {
		return serverId;
	}

	public void setServerId(String serverId) {
		this.serverId = serverId;
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

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public int getGameTime() {
		return gameTime;
	}

	public void setGameTime(int gameTime) {
		this.gameTime = gameTime;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public Date getLastUpgradeTime() {
		return lastUpgradeTime;
	}

	public void setLastUpgradeTime(Date lastUpgradeTime) {
		this.lastUpgradeTime = lastUpgradeTime;
	}

	public int getProperty() {
		return property;
	}

	public void setProperty(int property) {
		this.property = property;
	}

	public String getRegIp() {
		return regIp;
	}

	public void setRegIp(String regIp) {
		this.regIp = regIp;
	}

	public Date getRegTime() {
		return regTime;
	}

	public void setRegTime(Date regTime) {
		this.regTime = regTime;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public Date getLogoutTime() {
		return logoutTime;
	}

	public void setLogoutTime(Date logoutTime) {
		this.logoutTime = logoutTime;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public int getTodayPlayCount() {
		return todayPlayCount;
	}

	public void setTodayPlayCount(int todayPlayCount) {
		this.todayPlayCount = todayPlayCount;
	}

	public int getTodayPlayTime() {
		return todayPlayTime;
	}

	public void setTodayPlayTime(int todayPlayTime) {
		this.todayPlayTime = todayPlayTime;
	}

}