package com.ks.logic.event;

import java.util.Date;

import org.apache.log4j.Logger;

import com.ks.event.GameEvent;
import com.ks.logger.LoggerFactory;
import com.sf.data.DataSDK;
import com.sf.data.domain.LoginInfo;

public class DataLoginEvent extends GameEvent{
	private Logger logger=LoggerFactory.get(DataLoginEvent.class);
	
	private  LoginInfo info;
	
	public  DataLoginEvent(int roleId,Date loginTime,String ip,String pixel,String model,String net,String operator,String system){
		this.info=LoginInfo.create(roleId, loginTime, ip, pixel, model, net, operator, system);
	}
	@Override
	public void runEvent() {
		try {
			DataSDK.login(info);
		} catch (Exception e) {
			logger.error("",e);
		}
	}

}
