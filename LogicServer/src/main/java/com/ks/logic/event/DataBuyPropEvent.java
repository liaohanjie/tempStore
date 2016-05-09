package com.ks.logic.event;

import java.util.Date;

import org.apache.log4j.Logger;

import com.ks.event.GameEvent;
import com.ks.logger.LoggerFactory;
import com.sf.data.DataSDK;
/**
 * 购买道具log
 * @author living.li
 * @date 2014年7月14日
 */
public class DataBuyPropEvent extends GameEvent{

	private Logger logger=LoggerFactory.get(DataBuyPropEvent.class);
	private int partner;
	
	private int num;
	
	private int propId;
	
	private int propType;
	
	private int optType;
	
	private Date time;
	
	private int roleId;
	
	private String userName;
	
	public  DataBuyPropEvent(int partner,int roleId,String username,int propId, int propType,int num,int optType,Date time){
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
			DataSDK.buyProp(partner, roleId, userName, propId, propType, num, optType, time);
		} catch (Exception e) {
			logger.error("",e);
		}
		
	}
}
