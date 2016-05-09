package com.ks.logic.event;

import java.util.Date;

import org.apache.log4j.Logger;

import com.ks.event.GameEvent;
import com.ks.logger.LoggerFactory;

public class DataUsePropEvent extends GameEvent{
	private Logger logger=LoggerFactory.get(DataUsePropEvent.class);
	
	
	
	public  DataUsePropEvent(int partner,int roleId,String username,int propId, int propType,int num,int optType,Date time){
		/*this.partner=partner;
		this.num=num;
		this.userName=username;
		this.propType=propType;
		this.optType=optType;
		this.roleId=roleId;
		this.propId=propId;
		this.time=time;*/
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
