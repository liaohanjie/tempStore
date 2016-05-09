package com.ks.action.account;

import java.util.List;

import com.ks.model.account.Notice;
import com.ks.model.account.ServerInfo;

public interface ServerInfoAction {

	public String hello(String name);

	public List<ServerInfo> getServerList();

	public List<ServerInfo> getServerById(String serverId);

	List<Notice> getNotice();

	void addNotice(Notice notice);

	public void updateNotice(Notice notice);

	public void deleteNotice(int id);

	/**
	 * 根据区服ID查找区服信息
	 * 
	 * @param id
	 * @return
	 */
	public ServerInfo getServerInfoById(Integer id);

	/**
	 * 修改服务器
	 * 
	 * @param serverInfo
	 */
	public void updateServerInfo(ServerInfo serverInfo);

	/**
	 * 新增服务器
	 * 
	 * @param serverInfo
	 */
	public void addServerInfo(ServerInfo serverInfo);


}
