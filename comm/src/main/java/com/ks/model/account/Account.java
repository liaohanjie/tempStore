package com.ks.model.account;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户账号信息
 * @author zhoujf
 * @date 2015年6月23日
 */
public class Account  implements Serializable{
	
    private static final long serialVersionUID = -6692144137896841020L;
    
	private Integer id;
	/**合作商编号*/
	private Integer partnerId;
	/**游戏编号*/
	private Integer gameId;
	/**游戏角色编号*/
	private Integer userId;
	/**游戏登陆用户名*/
	private String userName;
	/**最后登陆区服编号*/
	private Integer lastLoginServerId;
	/**登陆次数*/
	private Integer loginCount;
	/**最后登陆时间*/
	private Date lastLoginTime;
	/**创建时间*/
	private Date createTime;
	/**状态: 1： 正常， 0： 禁止登陆*/
	private Integer status;
	/**登陆IP*/
	private String ip;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getPartnerId() {
		return partnerId;
	}
	public void setPartnerId(Integer partnerId) {
		this.partnerId = partnerId;
	}
	public Integer getGameId() {
		return gameId;
	}
	public void setGameId(Integer gameId) {
		this.gameId = gameId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Integer getLastLoginServerId() {
		return lastLoginServerId;
	}
	public void setLastLoginServerId(Integer lastLoginServerId) {
		this.lastLoginServerId = lastLoginServerId;
	}
	public Date getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public Integer getLoginCount() {
		return loginCount;
	}
	public void setLoginCount(Integer loginCount) {
		this.loginCount = loginCount;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
}
