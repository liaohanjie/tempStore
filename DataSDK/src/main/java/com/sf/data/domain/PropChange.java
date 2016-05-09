package com.sf.data.domain;

import java.io.Serializable;
import java.util.Date;
/**
 * 道具记录
 * @author living.li
 * @date 2014年7月8日
 */
public class PropChange implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String serverId;
	
	private int partner;
	
	private int num;
	
	private int propId;
	
	private int propType;
	
	private int optType;
	
	private Date time;
	
	private int roleId;
	
	private String userName;
	
	public static PropChange  create(String serverId,int partner,int roleId,String username,int propId, int propType,int num,int optType,Date time){
		PropChange pc=new PropChange();
		pc.setRoleId(roleId);
		pc.setUserName(username);
		pc.setPartner(partner);
		pc.setNum(num);
		pc.setOptType(optType);
		pc.setPropType(propType);
		pc.setPropId(propId);
		pc.setServerId(serverId);
		pc.setTime(time);
		return pc;
	}
	
	public int getPropId() {
		return propId;
	}

	public void setPropId(int propId) {
		this.propId = propId;
	}

	public int getPropType() {
		return propType;
	}

	public void setPropType(int propType) {
		this.propType = propType;
	}

	public String getServerId() {
		return serverId;
	}

	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
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

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public int getOptType() {
		return optType;
	}

	public void setOptType(int optType) {
		this.optType = optType;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}
	
	
	
}
