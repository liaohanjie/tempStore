package com.ks.action.world;

import com.ks.model.account.ServerInfo;

/**
 * 获取服务器配置信息
 * 
 * @author zhoujf
 * @date 2015年7月8日
 */
public interface WorldServerInfoAction {
	
	/**
	 * 获取服务器信息
	 * 
	 * @param serverId
	 * @return
	 */
	ServerInfo getServerInfo(String serverId);
}
