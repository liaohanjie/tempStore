package com.ks.logic.event;

import java.util.Date;

import org.apache.log4j.Logger;

import com.ks.event.GameEvent;
import com.ks.logger.LoggerFactory;

public class DataGivePropEvent extends GameEvent{

	private Logger logger=LoggerFactory.get(DataGivePropEvent.class);
	
	private int partner;
	
	private int num;
	
	private int propId;
	
	private int propType;
	
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

	private int optType;
	
	private Date time;
	
	private int roleId;
	
	private String userName;
	
	public  DataGivePropEvent(int partner,int roleId,String username,int propId, int propType,int num,int optType,Date time){
		this.partner=partner;
		this.num=num;
		this.userName=username;
		this.propType=propType;
		this.optType=optType;
		this.roleId=roleId;
		this.propId=propId;
		this.time=time;
	} 

	@Override
	public void runEvent() {
		try {
			//DataSDK.useProp(partner, roleId, userName, propId, propType, num, optType, time);
		} catch (Exception e) {
			logger.error("",e);
		}
	}
}
