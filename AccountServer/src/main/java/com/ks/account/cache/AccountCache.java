package com.ks.account.cache;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ks.account.kernel.AccountServerKernel;
import com.ks.account.service.AdminService;
import com.ks.account.service.ServerInfoService;
import com.ks.account.service.ServiceFactory;
import com.ks.model.account.Partner;
import com.ks.model.account.ServerInfo;
import com.ks.rpc.RPCKernel;

public class AccountCache implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static List<ServerInfo> serversList;
	private static Map<String,ServerInfo> serversMap;
	
	private static Map<Integer,Partner> parnterMap;
	private static Map<Integer,ServerInfo> idServersMap;
	
	private static Map<Integer,ServerInfo> serverNoServersMap;
	
	public static void init(){
		initServerInfo();
		initPartner();
	}	
	public static synchronized void initServerInfo(){
		Map<String, ServerInfo> map=new HashMap<>();
		Map<Integer, ServerInfo> idMap=new HashMap<>();
		Map<Integer, ServerInfo> serverNoMap=new HashMap<>();
		ServerInfoService infoService=ServiceFactory.getService(ServerInfoService.class);		
		List<ServerInfo> infos=infoService.getServerList();		
		for(ServerInfo info:infos){
			map.put(info.getServerId(), info);
			idMap.put(info.getId(), info);
			serverNoMap.put(info.getServerNo(), info);
			if(info.getStatus()>0){
				try{
					RPCKernel.getRPCClientById(info.getServerId());
				}catch(Exception e){//如果没有就连接
					String serverId = info.getServerId().trim();
					final String ip = info.getWorldIp();
					final int port = info.getWorldPort();
					String remote = serverId + ":" + ip + ":" + port;
					AccountServerKernel.connectServer(serverId, ip, port, remote);
				}
			}
		}
		serversList=infos;
		serversMap=map;
		idServersMap=idMap;
		serverNoServersMap = serverNoMap;
	}
	public static void initPartner(){
		Map<Integer, Partner> map=new HashMap<>();
		AdminService service=ServiceFactory.getService(AdminService.class);		
		List<Partner> parnters=service.queryParnters();
		for(Partner p:parnters){
			map.put(p.getParnterId(), p);
		}
		parnterMap=map;
	}
	public static List<ServerInfo> getServerList(){
		return serversList;
	}	
	public static ServerInfo getServerByServerId(String serverId){
		return serversMap.get(serverId);
	}
	public static ServerInfo getServerById(Integer id){
		return idServersMap.get(id);
	}
	public static Map<String, ServerInfo> getServersMap() {
		return serversMap;
	}
	public static Map<Integer,Partner> getPartnerMap(){
		return parnterMap;
	}
	public static void reload(){
		initServerInfo();
		initPartner();
	}
	
	public static ServerInfo getServerByServerNo(int serverNo){
		return serverNoServersMap.get(serverNo);
	}
	
	public static ServerInfo getServerByServerNo(String serverNo){
		int sNo = 0;
		try{
			sNo = Integer.parseInt(serverNo);
		}catch(Exception e){}
		return serverNoServersMap.get(sNo);
	}
}
