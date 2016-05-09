package com.ks.model.check;

import java.util.Date;

/**
 * 玩家可疑检查数据记录
 * @author hanjie.l
 *
 */
public class UserDoubtCheckLog {
	
	/**
	 * 主键id
	 */
	private int id;
	
	/**
	 * 玩家id
	 */
	private int userId;
	
	/**
	 * 谁提交的可疑记录
	 * 0 服务端  1客户端
	 */
	private int commiter;
	
	/**
	 * 客户端提供数据
	 */
	private String  clientData="";
	
	/**
	 * 服务端提供数据
	 */
	private String serverData="";
	
	/**
	 * 理由
	 */
	private String reason="";
	
	/**
	 * 创建时间
	 */
	private Date createTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getCommiter() {
		return commiter;
	}

	public void setCommiter(int commiter) {
		this.commiter = commiter;
	}

	public String getClientData() {
		return clientData;
	}

	public void setClientData(String clientData) {
		this.clientData = clientData;
	}

	public String getServerData() {
		return serverData;
	}

	public void setServerData(String serverData) {
		this.serverData = serverData;
	}

	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
}
