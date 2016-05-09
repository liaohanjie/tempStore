package com.sf.data.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户注册
 * @author living.li
 * @date 2014年7月1日
 */
public class UserRegister implements Serializable{
	
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int gameId;
	/**服务器*/
	private String serverId;
	/**渠道*/
	private int partner;
	/**角色ID*/
	private int roleId;
	/**角色名*/
	private String roleName;
	/**注册IP*/
	private String regIp;
	/**注册时间*/
	private Date regTime;
	/**mac地址*/
	private String mac;
	/**像素*/
	private String pixel;
	/**手机型号*/
	private String model;
	/**连网方式*/
	private String net;
	/**运营商*/
	private String operator;
	
	private String system;
	
	
	
	public static UserRegister create(int roleId,String roleName,String regIp,Date regTime,
			String pixel,String model,
			String net,String operator,String system){
		UserRegister reg=new UserRegister();
		reg.setRoleId(roleId);
		reg.setRoleName(roleName);
		reg.setRegIp(regIp);
		reg.setRegTime(regTime);
		reg.setPixel(pixel);
		reg.setModel(model);
		reg.setNet(net);
		reg.setOperator(operator);
		reg.setSystem(system);
		return reg;
	}

	public String getServerId() {
		return serverId;
	}

	public int getGameId() {
		return gameId;
	}

	public String getSystem() {
		return system;
	}

	public void setSystem(String system) {
		this.system = system;
	}

	public void setGameId(int gameId) {
		this.gameId = gameId;
	}

	public void setServerId(String serverId) {
		this.serverId = serverId;
	}

	public int getPartner() {
		return partner;
	}

	public void setPartner(int partner) {
		this.partner = partner;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

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

	public String getRegIp() {
		return regIp;
	}

	public void setRegIp(String regIp) {
		this.regIp = regIp;
	}

	public Date getRegTime() {
		return regTime;
	}

	public String getPixel() {
		return pixel;
	}

	public void setPixel(String pixel) {
		this.pixel = pixel;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
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
	public void setRegTime(Date regTime) {
		this.regTime = regTime;
	}

	@Override
	public String toString() {
		return "UserRegister [serverId=" + serverId + ", partner=" + partner
				+ ", roleId=" + roleId + ", roleName=" + roleName + ", regIp="
				+ regIp + ", regTime=" + regTime + "]";
	}
	
	
	
}
