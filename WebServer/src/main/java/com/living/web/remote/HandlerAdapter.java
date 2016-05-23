package com.living.web.remote;

import com.ks.action.account.AdminAction;
import com.ks.action.account.GiftCodeAction;
import com.ks.action.account.PayAction;
import com.ks.action.account.ServerInfoAction;
import com.ks.action.world.WorldUserAction;
import com.ks.app.Application;
import com.ks.rpc.RPCKernel;
import com.living.web.hanlder.BaseHandler;

public class HandlerAdapter extends BaseHandler { 	
	public <T>T worldAction(String serverId,Class<T> clazz){
			return RPCKernel.getRemoteByServerId(serverId,clazz);		
	}	
	public static <T>T getInfoAction(Class<T> clazz){
			return RPCKernel.getRemoteByServerType(Application.ACCOUNT_SERVER, clazz);	
	}
	public AdminAction adminAction(){
		return getInfoAction(AdminAction.class);
	}
	public GiftCodeAction giftCodeAction(){
		return getInfoAction(GiftCodeAction.class);
	}
	public ServerInfoAction serverInfoAction(){
		return getInfoAction(ServerInfoAction.class);
	}
	public PayAction payAction(){
		return getInfoAction(PayAction.class);
	}
	public WorldUserAction worldUserAction(String serverId){
		return worldAction(serverId, WorldUserAction.class);
	}
}
