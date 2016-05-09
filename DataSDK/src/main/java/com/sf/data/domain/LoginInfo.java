package com.sf.data.domain;

import java.io.Serializable;
import java.util.Date;
/**
 * 用户登入
 * @author living.li
 * @date 2014年7月8日
 */
public class LoginInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int gameId;
	
	private String serverId;
	
	private int roleId;
	
	private Date loginTime;
	
	/**像素*/
	private String pixel;
	/**手机型号*/
	private String model;
	/**连网方式*/
	private String net;
	/**运营商*/
	private String operator;
	/**系统*/
	private String system;
	/**ip*/
	private String ip;
	
	
	
	public static LoginInfo create(int roleId,
			Date loginTime,String ip,
			String pixel,String model,
			String net,String operator,String system){
		LoginInfo login=new LoginInfo();
		login.setRoleId(roleId);
		login.setIp(ip);
		login.setLoginTime(loginTime);
		login.setOperator(operator);
		login.setPixel(pixel);
		login.setNet(net);
		login.setSystem(system);
		return login;
	}

	
	public String getSystem() {
		return system;
	}
	public void setSystem(String system) {
		this.system = system;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getPixel() {
		return pixel;
	}
	public void setPixel(String pixel) {
		this.pixel = pixel;
	}
	public String getNet() {
		return net;
	}
	public void setNet(String net) {
		this.net = net;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public int getGameId() {
		return gameId;
	}
	public void setGameId(int gameId) {
		this.gameId = gameId;
	}
	public String getServerId() {
		return serverId;
	}

	public void setServerId(String serverId) {
		this.serverId = serverId;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public Date getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}


	@Override
	public String toString() {
		return "LoginInfo [gameId=" + gameId + ", serverId=" + serverId
				+ ", roleId=" + roleId + ", loginTime=" + loginTime
				+ ", pixel=" + pixel + ", model=" + model + ", net=" + net
				+ ", operator=" + operator + ", system=" + system + ", ip="
				+ ip + "]";
	}
	
	
}
