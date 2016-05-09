package com.ks.action.game;

import java.util.List;

import com.ks.rpc.ServerInfo;
import com.ks.rpc.Timeout;

public interface GameServerAction{
	/**
	 * 增加数据库远程调用
	 * @param databaseServerInfos 数据库远程调用
	 * @throws Exception 
	 */
	@Timeout(time=20*1000L)
	void addLogicServerRPC(List<ServerInfo> logicServerInfos,String serverId) throws Exception;
	/**
	 * 增加数据库服务器远程调用
	 * @param dataInfoServerInfo 数据库服务器信息
	 * @throws Exception 
	 */
	@Timeout(time=20*1000L)
	void addLogicServerRPC(ServerInfo logicServerInfo) throws Exception;
	/**
	 * 
	 * @return
	 */
	void ping();
}