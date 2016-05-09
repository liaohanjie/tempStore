package com.ks.game.action;

import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.Map;

import com.ks.action.game.GameServerAction;
import com.ks.app.Application;
import com.ks.rpc.RPCKernel;
import com.ks.rpc.ServerInfo;
import com.ks.rpc.client.ClientRPCHandler;
import com.ks.rpc.client.RPCClient;

public final class GameServerActionImpl implements GameServerAction {

	@Override
	public void addLogicServerRPC(List<ServerInfo> logicServerInfos, String serverId) throws Exception {
		if (logicServerInfos == null || logicServerInfos.size() == 0) {
			return;
		}
		for (ServerInfo serverInfo : logicServerInfos) {
			RPCClient client = new RPCClient(new InetSocketAddress(serverInfo.getHost(), serverInfo.getPort()), serverInfo.getServerId(), Application.LOGIC_SERVER);
			RPCKernel.addRPCClient(client);
			Map<String, Class<?>> map = Application.RPC_CLIENT_MAPPER.get(Application.LOGIC_SERVER);
			for (Map.Entry<String, Class<?>> e : map.entrySet()) {
				RPCKernel.addServerIDRemote(serverInfo.getServerId(), e.getValue(),
				        Proxy.newProxyInstance(e.getValue().getClassLoader(), new Class<?>[] { e.getValue() }, new ClientRPCHandler(e.getKey(), RPCKernel.FLAG_ID, 0, serverInfo.getServerId())));
			}
		}
	}

	@Override
	public void addLogicServerRPC(ServerInfo logicServerInfo) throws Exception {
		RPCClient client = new RPCClient(new InetSocketAddress(logicServerInfo.getHost(), logicServerInfo.getPort()), logicServerInfo.getServerId(), Application.LOGIC_SERVER);
		RPCKernel.addRPCClient(client);

		Map<String, Class<?>> map = Application.RPC_CLIENT_MAPPER.get(Application.LOGIC_SERVER);
		for (Map.Entry<String, Class<?>> e : map.entrySet()) {
			RPCKernel.addServerIDRemote(logicServerInfo.getServerId(), e.getValue(),
			        Proxy.newProxyInstance(e.getValue().getClassLoader(), new Class<?>[] { e.getValue() }, new ClientRPCHandler(e.getKey(), RPCKernel.FLAG_ID, 0, logicServerInfo.getServerId())));
		}
	}

	@Override
	public void ping() {
	}

}
