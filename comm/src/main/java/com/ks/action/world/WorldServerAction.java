package com.ks.action.world;

import com.ks.rpc.Timeout;


/**
 * 世界服务器处理
 * @author ks
 *
 */
public interface WorldServerAction{
	/**
	 * 游戏服务器连接
	 * @param host 游戏服务器外网地址
	 * @param port 游戏服务器外网端口
	 * @param rpcHost 游戏服务器远程调用地址
	 * @param rpcPort 游戏服务器远程调用端口
	 * @throws Exception 
	 */
	@Timeout(time=20*1000L)
	void gameServerConnected(String host,int port,String rpcHost,int rpcPort,String serverId) throws Exception;
	/**
	 * 数据库服务器连接
	 * @param host 数据库服务器地址
	 * @param port 数据库服务器端口
	 * @throws Exception 
	 */
	@Timeout(time=20*1000L)
	void logicServerConndeted(String host,int port,String serverId) throws Exception;
}