package com.sf.data.domain;

import java.io.Serializable;
import java.util.Date;
/**
 * 服务器在线统计
 * @author living.li
 * @date 2014年7月8日
 */
public class ServerOnline implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private  Date time;
	/**平均在线*/
	private  int num;
	
	private String serverId;
	
	private int gameId;
	
	private int partner;
	
	public static ServerOnline create(int gameId,String serverId,int num,Date time){
		ServerOnline so=new ServerOnline();
		so.setGameId(gameId);
		so.setServerId(serverId);
		so.setNum(num);
		so.setTime(time);
		return so;
	}
	
	public int getPartner() {
		return partner;
	}

	public void setPartner(int partner) {
		this.partner = partner;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public int getGameId() {
		return gameId;
	}

	public void setGameId(int gameId) {
		this.gameId = gameId;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}

	public String getServerId() {
		return serverId;
	}

	public void setServerId(String serverId) {
		this.serverId = serverId;
	}
	
	

}
