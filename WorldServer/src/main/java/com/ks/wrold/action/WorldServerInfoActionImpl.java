package com.ks.wrold.action;

import java.util.List;

import com.ks.action.account.ServerInfoAction;
import com.ks.action.world.WorldServerInfoAction;
import com.ks.app.Application;
import com.ks.model.account.ServerInfo;
import com.ks.rpc.RPCKernel;

public class WorldServerInfoActionImpl implements WorldServerInfoAction {
	
	@Override
    public ServerInfo getServerInfo(String serverId) {
		ServerInfoAction action = RPCKernel.getRemoteByServerType(Application.ACCOUNT_SERVER, ServerInfoAction.class);
		List<ServerInfo> list = action.getServerById(serverId);
		if(list != null && !list.isEmpty()) {
			return list.get(0);	
		}
	    return null;
    }
	
}