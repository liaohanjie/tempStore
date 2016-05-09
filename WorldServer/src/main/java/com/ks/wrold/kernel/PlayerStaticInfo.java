package com.ks.wrold.kernel;

import java.io.Serializable;

/**
 * 用户静态信息
 * @author ks
 *
 */
public final class PlayerStaticInfo implements Serializable{
	
	private static final long serialVersionUID = -7443821858864443018L;
	/**用户编号*/
	private int userId;
	/**合作方*/
	private int partner;
	/**用户名*/
	private String username;
	/**会话编号*/
	private long sessionId;
	/**所在游戏服务器编号*/
	private String gameServerId;
	/**最后发送聊天时间*/
	private long lastSendChatTime;
	/**最后聊天轮询id*/
	private long lastPollMessageId;
	/**最后拉取跑马灯时间*/
	private long lastPollMaqueeTime;
	
	public int getPartner() {
		return partner;
	}
	public void setPartner(int partner) {
		this.partner = partner;
	}
	public long getSessionId() {
		return sessionId;
	}
	public void setSessionId(long sessionId) {
		this.sessionId = sessionId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public String getGameServerId() {
		return gameServerId;
	}
	public void setGameServerId(String gameServerId) {
		this.gameServerId = gameServerId;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public long getLastPollMessageId() {
		return lastPollMessageId;
	}
	public void setLastPollMessageId(long lastPollMessageId) {
		this.lastPollMessageId = lastPollMessageId;
	}
	public long getLastSendChatTime() {
		return lastSendChatTime;
	}
	public void setLastSendChatTime(long lastSendChatTime) {
		this.lastSendChatTime = lastSendChatTime;
	}
	public long getLastPollMaqueeTime() {
		return lastPollMaqueeTime;
	}
	public void setLastPollMaqueeTime(long lastPollMaqueeTime) {
		this.lastPollMaqueeTime = lastPollMaqueeTime;
	}
}
