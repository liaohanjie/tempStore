package com.ks.model.violation;

import java.io.Serializable;
import java.util.Date;

/**
 * 违规日志
 * 
 * @author lipp 2016年1月23日
 */
public class ViolationLogger implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static int VIOLATION_TYPE_禁言 = 1;
	public static int VIOLATION_TYPE_封号 = 2;

	private int id;

	/** 服务器ID */
	private String serverId;

	/** 违规用户ID */
	private int userId;

	/** 违规类型 */
	private int type;

	/** 违规备注 */
	private String description;

	/** 禁用时间 */
	private Date forbideenTime;

	/** 创建时间 */
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

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Date getForbideenTime() {
		return forbideenTime;
	}

	public void setForbideenTime(Date forbideenTime) {
		this.forbideenTime = forbideenTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getDescription() {
	    return description;
    }

	public void setDescription(String description) {
	    this.description = description;
    }

	public String getServerId() {
	    return serverId;
    }

	public void setServerId(String serverId) {
	    this.serverId = serverId;
    }
}
