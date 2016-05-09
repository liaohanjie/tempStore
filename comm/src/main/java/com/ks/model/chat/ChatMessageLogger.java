package com.ks.model.chat;

import java.io.Serializable;
import java.util.Date;

/**
 * 聊天日志
 * 
 * @author lipp 2016年1月4日
 */
public class ChatMessageLogger implements Serializable {

	private static final long serialVersionUID = -4658828900004250768L;

	private int id;
	/** 服务器ID */
	private String serverId;
	/** 聊天类型 */
	private byte type;
	/** 发送者ID */
	private int sendUserId;
	/** 接受者ID */
	private int receiverId;
	/** 发送内容 */
	private String content;
	/** 创建时间 */
	private Date createTime;

	public ChatMessageLogger() {
	}

	public ChatMessageLogger(byte type, int sendUserId, int receiverId, String content, Date createTime) {
		this.type = type;
		this.sendUserId = sendUserId;
		this.receiverId = receiverId;
		this.content = content;
		this.createTime = createTime;
	}

	public byte getType() {
		return type;
	}

	public void setType(byte type) {
		this.type = type;
	}

	public int getSendUserId() {
		return sendUserId;
	}

	public void setSendUserId(int sendUserId) {
		this.sendUserId = sendUserId;
	}

	public int getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(int receiverId) {
		this.receiverId = receiverId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getServerId() {
		return serverId;
	}

	public void setServerId(String serverId) {
		this.serverId = serverId;
	}
}
