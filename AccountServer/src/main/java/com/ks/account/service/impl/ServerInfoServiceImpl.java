package com.ks.account.service.impl;

import java.util.List;

import com.ks.account.cache.AccountCache;
import com.ks.account.service.BaseService;
import com.ks.account.service.ServerInfoService;
import com.ks.model.account.Notice;
import com.ks.model.account.ServerInfo;

public class ServerInfoServiceImpl extends BaseService implements
		ServerInfoService {
	@Override
	public List<ServerInfo> getServerList() {
		return serverInfoDAO.queryServerList();
	}

	@Override
	public List<Notice> getNotices() {
		return serverInfoDAO.getNoticeAll();
	}

	@Override
	public void addNotice(Notice notice) {
		serverInfoDAO.addNotice(notice);
	}

	@Override
	public void updateNotice(Notice notice) {
		serverInfoDAO.updateNotice(notice);
	}

	@Override
	public List<ServerInfo> getServerById(String serverId) {
		return serverInfoDAO.queryServerById(serverId);

	}

	@Override
	public ServerInfo getServerInfoById(Integer id) {
		return serverInfoDAO.queryServerInfoById(id);
	}

	@Override
	public void updateServerInfo(ServerInfo serverInfo) {
		serverInfoDAO.updateServerInfo(serverInfo);
		AccountCache.initServerInfo();
	}

	@Override
	public void addServerInfo(ServerInfo serverInfo) {
		serverInfoDAO.addServerInfo(serverInfo);
		AccountCache.initServerInfo();
	}

	@Override
    public void deleteNotice(int id) {
	    serverInfoDAO.deleteNotice(id);
    }

}
