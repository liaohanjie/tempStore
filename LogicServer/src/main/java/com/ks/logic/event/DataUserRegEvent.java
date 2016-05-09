package com.ks.logic.event;

import java.util.Date;

import org.apache.log4j.Logger;

import com.ks.event.GameEvent;
import com.ks.logger.LoggerFactory;
import com.sf.data.DataSDK;
import com.sf.data.domain.UserRegister;

public class DataUserRegEvent extends GameEvent{
	private Logger logger=LoggerFactory.get(DataUserRegEvent.class);

	private UserRegister reg;
	
	public DataUserRegEvent(int roleId,
			String roleName,String regIp,
			Date regTime,String pixel,
			String model,String net,
			String operator,String system){
		reg=UserRegister.create(roleId, roleName, regIp, regTime, pixel, model, net, operator, system);
		
	}
	@Override
	public void runEvent() {
		try {
			DataSDK.userRegist(reg);
		} catch (Exception e) {
			logger.error("",e);
		}
	}

}
