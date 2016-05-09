package com.ks.protocol.vo.login;

import com.ks.protocol.Message;
/**
 * 登录结果
 * @author ks.wu
 *
 */
public class LoginResultVO extends Message {

	private static final long serialVersionUID = 1L;
	/**用户编号*/
	private int userId;
	/**会话编号*/
	private long sessionId;
	/**游戏服务器ip*/
	private String gameServerHost;
	/**游戏服务器端口*/
	private int gameServerPort;
	
	public long getSessionId() {
		return sessionId;
	}
	public void setSessionId(long sessionId) {
		this.sessionId = sessionId;
	}
	public String getGameServerHost() {
		return gameServerHost;
	}
	public void setGameServerHost(String gameServerHost) {
		this.gameServerHost = gameServerHost;
	}
	public int getGameServerPort() {
		return gameServerPort;
	}
	public void setGameServerPort(int gameServerPort) {
		this.gameServerPort = gameServerPort;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
}
