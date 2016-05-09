package com.ks.model.chat;

import java.io.Serializable;

/**
 * 聊天信息
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2015年12月28日
 */
public class ChatMessage implements Serializable {

    private static final long serialVersionUID = -4658828900004250768L;

    /**聊天类型*/
    private byte type;
    /**发送者ID*/
    private int sendUserId;
    /**接受者ID*/
    private int receiverId;
    /**发送内容*/
    private String content;
    /**创建时间*/
    private long createTime;
    
    public ChatMessage() {
    }
    
	public ChatMessage(byte type, int sendUserId, int receiverId, String content, long createTime) {
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
	public long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
}
