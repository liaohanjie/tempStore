package com.ks.model.filter;

import java.util.Date;

public class AdminFilter extends Filter {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int adminId;
	/** 用户名 */
	private String userName;
	/** 密码 */
	private String password;
	/** 角色 */
	private String roleList;
	/** 创建时间 */
	private Date createTime;
	/** 修改时间 */
	private Date updateTime;
	
	private String permissionList;
	public int getAdminId() {
		return adminId;
	}

	public void setAdminId(int adminId) {
		this.adminId = adminId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getRoleList() {
		return roleList;
	}

	public void setRoleList(String roleList) {
		this.roleList = roleList;
	}

	public String getPermissionList() {
		return permissionList;
	}

	public void setPermissionList(String permissionList) {
		this.permissionList = permissionList;
	}


}
