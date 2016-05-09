package com.sf.data.domain;

import java.io.Serializable;
import java.util.Date;
/**
 * 用户登出
 * @author living.li
 * @date 2014年7月8日
 */
public class LoginOutInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int gameId;
	
	private String serverId;
	
	private int partner;

	private int roleId;

	private int grade;
	

	private Date loginTime;
	
	private Date loginOutTime;
	
	
	public static LoginOutInfo create(int roleId,int partner,int grade,Date loginTime,Date loginOutTime){
		LoginOutInfo login=new LoginOutInfo();
		login.setRoleId(roleId);
		login.setGrade(grade);
		login.setLoginTime(loginTime);
		login.setLoginOutTime(loginOutTime);
		login.setPartner(partner);
		return login;
	}
	public int getGameId() {
		return gameId;
	}
	public void setGameId(int gameId) {
		this.gameId = gameId;
	}
	public String getServerId() {
		return serverId;
	}
	public int getPartner() {
		return partner;
	}
	public void setPartner(int partner) {
		this.partner = partner;
	}
	public void setServerId(String serverId) {
		this.serverId = serverId;
	}
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public int getGrade() {
		return grade;
	}
	public void setGrade(int grade) {
		this.grade = grade;
	}
	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public Date getLoginOutTime() {
		return loginOutTime;
	}

	public void setLoginOutTime(Date loginOutTime) {
		this.loginOutTime = loginOutTime;
	}
	
}
