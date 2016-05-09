package com.ks.model.account;

import java.io.Serializable;
import java.util.Date;

public class GiftCodeLogger implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int id;

	/** 用户ID */
	private int userId;

	/** 激活码 */
	private String code;

	/** 奖励ID */
	private String awardId;

	/** 创建时间 */
	private Date createTime;
	
	private String serverId;

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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getAwardId() {
		return awardId;
	}

	public void setAwardId(String awardId) {
		this.awardId = awardId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getServerId() {
	    return serverId;
    }

	public void setServerId(String serverId) {
	    this.serverId = serverId;
    }

}
