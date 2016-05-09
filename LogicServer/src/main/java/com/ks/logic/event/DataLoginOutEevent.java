package com.ks.logic.event;

import java.util.Date;

import org.apache.log4j.Logger;

import com.ks.event.GameEvent;
import com.ks.logger.LoggerFactory;
import com.sf.data.DataSDK;
import com.sf.data.domain.LoginOutInfo;

public class DataLoginOutEevent extends GameEvent{
	private Logger logger=LoggerFactory.get(DataLoginOutEevent.class);

	
	private  LoginOutInfo outinfo;
	
	public DataLoginOutEevent(int roleId,int partner,int grade,Date loginTime,Date loginOutTime){
		outinfo=LoginOutInfo.create(roleId,partner, grade,loginTime, loginOutTime);
	}
	@Override
	public void runEvent() {
		try {
			DataSDK.loginOut(outinfo);
		} catch (Exception e) {
			logger.error("",e);
		}
	}

}
