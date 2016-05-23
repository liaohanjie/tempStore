package com.ks.account.action;

import java.util.List;

import com.ks.account.service.ServerInfoService;
import com.ks.account.service.ServiceFactory;
import com.ks.action.account.ServerInfoAction;
import com.ks.model.account.Notice;
import com.ks.model.account.ServerInfo;

/**
 * 
 * @author living.li
 * @date 2014年4月19日
 */
public class ServerInfoActionImpl implements ServerInfoAction {
	private static final ServerInfoService serverInfoService = ServiceFactory
			.getService(ServerInfoService.class);

	@Override
	public String hello(String name) {
		return "living";
	}

	@Override
	public List<ServerInfo> getServerList() {
		return serverInfoService.getServerList();
	}

	@Override
	public List<Notice> getNotice() {
		return serverInfoService.getNotices();
	}

	@Override
	public void addNotice(Notice notice) {
		serverInfoService.addNotice(notice);
	}

	@Override
	public void updateNotice(Notice notice) {
		serverInfoService.updateNotice(notice);
	}

	@Override
	public List<ServerInfo> getServerById(String serverId) {
		return serverInfoService.getServerById(serverId);
	}

	@Override
	public ServerInfo getServerInfoById(Integer id) {
		return serverInfoService.getServerInfoById(id);
	}

	@Override
	public void updateServerInfo(ServerInfo serverInfo) {
		serverInfoService.updateServerInfo(serverInfo);
	}

	@Override
	public void addServerInfo(ServerInfo serverInfo) {
		serverInfoService.addServerInfo(serverInfo);
	}

	@Override
    public void deleteNotice(int id) {
		serverInfoService.deleteNotice(id);
    }

}
