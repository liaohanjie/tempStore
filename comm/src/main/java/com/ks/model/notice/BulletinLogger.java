package com.ks.model.notice;

import java.io.Serializable;
import java.util.Date;

import com.ks.model.filter.Filter;

public class BulletinLogger extends Filter implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	/** 操作者 */
	private String author;
	/** 发送内容 */
	private String content;
	/** 发送的服务器 */
	private String serverId;
	private Date createTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getServerId() {
		return serverId;
	}

	public void setServerId(String serverId) {
		this.serverId = serverId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getContent() {
	    return content;
    }

	public void setContent(String content) {
	    this.content = content;
    }

}
