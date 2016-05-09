package com.ks.protocol.vo.chat;

import com.ks.protocol.Message;

/**
 * 聊天信息
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2015年12月28日
 */
public class ChatMsgRequestVO extends Message {

    private static final long serialVersionUID = -7371236179190805802L;
	
    /**发送类型*/
    private byte type;
    /**发送者编号*/
    private int sendUserId;
    /**接受者编号*/
    private int receiverId;
	/**聊天内容*/
    private String content;
    
	public void init(byte type, int sendUserId, int receiverId, String content) {
	    this.type = type;
	    this.sendUserId = sendUserId;
	    this.receiverId = receiverId;
	    this.content = content;
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getReceiverId() {
		return receiverId;
	}
	public void setReceiverId(int receiverId) {
		this.receiverId = receiverId;
	}
}
