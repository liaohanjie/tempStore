package com.ks.protocol.vo.chat;

import com.ks.protocol.Ignore;
import com.ks.protocol.Message;
import com.ks.protocol.vo.user.UserCapVO;

/**
 * 聊天信息
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2015年12月28日
 */
public class ChatMsgResponseVO extends Message {

    private static final long serialVersionUID = -3037725559291197586L;
    
	/**发送类型*/
    @Ignore
    private long id;
	/**发送类型*/
    private byte type;
    /**聊天内容*/
    private String content;
    /**发送时间*/
    private long createTime;
    /**魂钻充值总数*/
    private int totalCurrency;
    /**发送者信息*/
    private UserCapVO userCapVO;
    
	public void init(byte type, String content, int totalCurrency, long createTime, UserCapVO userCapVO) {
	    this.type = type;
	    this.content = content;
	    this.totalCurrency = totalCurrency;
	    this.createTime = createTime;
	    this.userCapVO = userCapVO;
    }
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public byte getType() {
		return type;
	}
	public void setType(byte type) {
		this.type = type;
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
	public UserCapVO getUserCapVO() {
		return userCapVO;
	}
	public void setUserCapVO(UserCapVO userCapVO) {
		this.userCapVO = userCapVO;
	}
	public int getTotalCurrency() {
		return totalCurrency;
	}
	public void setTotalCurrency(int totalCurrency) {
		this.totalCurrency = totalCurrency;
	}
}
