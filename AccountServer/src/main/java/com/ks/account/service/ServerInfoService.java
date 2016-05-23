package com.ks.account.service;

import java.util.List;

import com.ks.access.Transaction;
import com.ks.model.account.Notice;
import com.ks.model.account.ServerInfo;

/**
 * 
 * @author living.li
 * @date 2014年4月9日
 */
public interface ServerInfoService {

	/**
	 * 根据ID查找服务器信息
	 * 
	 * @param id
	 * @return
	 */
	public ServerInfo getServerInfoById(Integer id);

	public List<ServerInfo> getServerList();

	public List<ServerInfo> getServerById(String serverId);

	public List<Notice> getNotices();

	@Transaction
	public void addNotice(Notice notice);

	@Transaction
	public void updateNotice(Notice notice);

	@Transaction
	public void deleteNotice(int id);

	/**
	 * 修改服务器
	 * 
	 * @param serverInfo
	 */
	@Transaction
	public void updateServerInfo(ServerInfo serverInfo);

	/**
	 * 新增服务器
	 * 
	 * @param serverInfo
	 */
	@Transaction
	public void addServerInfo(ServerInfo serverInfo);

}
