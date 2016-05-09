package com.sf.data.domain;

import java.io.Serializable;
/**
 * 用户引导
 * @author living.li
 * @date 2014年7月8日
 */
public class GuideNext implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int GameId;
	/**服务器ID*/
	private String serverId;
	
	private int stepId;
	
	private int roleId;

	
	public static GuideNext create(int gameId,int partner,int stepId,int roleId){
		GuideNext guid=new GuideNext();
		guid.setRoleId(roleId);
		guid.setStepId(stepId);
		guid.setGameId(gameId);
		return guid;
	}
	public String getServerId() {
		return serverId;
	}

	
	public int getGameId() {
		return GameId;
	}
	public void setGameId(int gameId) {
		GameId = gameId;
	}
	public void setServerId(String serverId) {
		this.serverId = serverId;
	}
	public int getStepId() {
		return stepId;
	}
	public void setStepId(int stepId) {
		this.stepId = stepId;
	}
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	
}
