package com.living.web;

import org.apache.log4j.Logger;

import com.ks.logger.LoggerFactory;
import com.living.pay.OrderHandler;
import com.living.web.hander.ActivityHanlder;
import com.living.web.hander.ServerInfoHandler;
import com.living.web.hander.UserHandler;
import com.living.web.hanlder.WebHandlerManager;
import com.living.web.remote.HandlerAdapter;
/**
 * 
 * @author living.li
 * @date   2014年5月10日
 */
public  class WebServer extends HandlerAdapter  {
	
	private static Logger logger=LoggerFactory.get(WebServer.class);
	
	private static class InstanceHolder {
		static WebServer instance = new WebServer();
	}
	public static WebServer get() {
		return InstanceHolder.instance;
	}	
	public void startUp() throws Exception{
		initWebHandler();		
	}	
	public void initWebHandler(){
		logger.info("init web hanlder--------------------------------------");
		WebHandlerManager.get().registHandler(new ActivityHanlder());
		WebHandlerManager.get().registHandler(new ServerInfoHandler());
		WebHandlerManager.get().registHandler(new UserHandler());
		WebHandlerManager.get().registHandler(new OrderHandler());
	}
	
}
