package com.sf.data.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author living.li
 * @date 2014年7月8日
 */
public class GameLevel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String serverId;
	
	private int partner;
	
	private int gameLevelId;
	
	private boolean isWin;
	
	private Date time;

	public static GameLevel create(int partner,int gameLevelId,Date time,boolean win){
		GameLevel gl=new GameLevel();
		gl.setPartner(partner);
		gl.setGameLevelId(gameLevelId);
		gl.setTime(time);
		gl.setWin(win);
		return gl;
	}
	
	
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

	public int getGameLevelId() {
		return gameLevelId;
	}

	public void setGameLevelId(int gameLevelId) {
		this.gameLevelId = gameLevelId;
	}

	public boolean isWin() {
		return isWin;
	}

	public void setWin(boolean isWin) {
		this.isWin = isWin;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}
	
	
	

}
