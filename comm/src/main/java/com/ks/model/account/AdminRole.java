package com.ks.model.account;

import java.io.Serializable;

/**
 * 用户角色
 * 
 * @author lipp 2015年4月21日
 */
public class AdminRole implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static int ROLE_ID_超管=1000000;
	
	/** 角色ID */
	private int roleId;
	/** 角色名 */
	private String roleName;
	/** 权限 */
	private String permission;
	/** 职位 */
	private String job;
	/** 描述 */
	private String des;

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}
}
