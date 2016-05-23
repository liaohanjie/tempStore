package com.living.web.remote;

import java.io.InputStream;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ks.app.Application;
import com.ks.logger.LoggerFactory;
import com.ks.rpc.RPCKernel;
import com.ks.rpc.client.ClientRPCHandler;
import com.ks.rpc.client.RPCClient;
import com.living.web.jetty.WebServerKernel;
/**
 * 
 * @author living.li
 * @date   2014年5月10日
 */
public class RemoteManager extends HandlerAdapter {
	private static final Logger logger=LoggerFactory.get(RemoteManager.class);

	public static void initAccount(InputStream appXmlInput) throws Exception{
		logger.info("init rpc connection");
		Application application = new Application();
		application.init(appXmlInput,application);
		String[] list=WebServerKernel.getConfig("account.servers","").split(";");
		for(String remote:list){
			String config[]=remote.split(":");
			String serverId=config[0].trim();
			final String ip=config[1].trim();
			final int port=Integer.valueOf(config[2].trim());
			
			logger.info("add client >>>>>>>>>>>>>>>>>"+serverId+">>"+remote+"");
			for (int i = 0; i < Runtime.getRuntime().availableProcessors(); i++) {
				RPCClient client = new RPCClient(new InetSocketAddress(ip,
						port), serverId, Application.ACCOUNT_SERVER);
				RPCKernel.addRPCClient(client);
			}	
			
			Map<String,Class<?>> map = Application.RPC_CLIENT_MAPPER.get(Application.ACCOUNT_SERVER);
			
			for(Map.Entry<String, Class<?>> e : map.entrySet()){
				ClientRPCHandler hanlder = new ClientRPCHandler(e.getKey(),	RPCKernel.FLAG_ID, 0, serverId);
				Object Instance = Proxy.newProxyInstance(e.getValue().getClassLoader(), new Class<?>[] { e.getValue()},hanlder);
				RPCKernel.addServerIDRemote(serverId, e.getValue(), Instance);
			}
		}
	}
	public static void initWorld() throws Exception{		
		/*ServerInfoAction serverInfoAction=getInfoAction(ServerInfoAction.class);
		List<ServerInfo> worldList=serverInfoAction.getServerList();		
		for(ServerInfo info:worldList){
			String serverId=info.getServerId();
			final String ip=info.getWorldIp().trim();
			final int port=info.getWorldPort();
			final String remote=serverId+":"+ip+":"+port;
			logger.info("add client >>>>>>>>>>>>>>>>>"+serverId+">>"+remote+"");
			for (int i = 0; i < Runtime.getRuntime().availableProcessors(); i++) {
				RPCClient client = new RPCClient(new InetSocketAddress(ip,
						port), "", Application.WORLD_SERVER);
				RPCKernel.addRPCClient(client);
			}			
			logger.info("init remote action:"+remote+" ---------------------------");
			Map<String,Class<?>> map = Application.RPC_CLIENT_MAPPER.get(Application.WORLD_SERVER);
			for(Map.Entry<String, Class<?>> e : map.entrySet()){
				ClientRPCHandler hanlder = new ClientRPCHandler(e.getKey(),	RPCKernel.FLAG_ID, 0, serverId);
				Object Instance = Proxy.newProxyInstance(e.getValue().getClassLoader(), new Class<?>[] { e.getValue()},hanlder);
				RPCKernel.addServerIDRemote(serverId, e.getValue(), Instance);
			}
		}		*/
	}
}
