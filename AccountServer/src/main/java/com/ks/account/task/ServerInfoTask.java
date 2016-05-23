/**
 * 
 */
package com.ks.account.task;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.ks.account.service.ServerInfoService;
import com.ks.account.service.ServiceFactory;
import com.ks.logger.LoggerFactory;
import com.ks.model.account.ServerInfo;
import com.ks.timer.task.BaseTask;
import com.ks.timer.task.Task;
import com.ks.util.HttpUtil;

/**
 * 区服状态更新
 * 
 * @author lipp 2016年1月25日
 */
@Task(initialDelay = 1, period = 1, unit = TimeUnit.MINUTES)
public class ServerInfoTask extends BaseTask {

	private Logger logger = LoggerFactory.get(ServerInfoTask.class);

	private ServerInfoService serverInfoService = ServiceFactory.getService(ServerInfoService.class);

	@Override
	public void runTask() {
		_toNormal();
		_toMaintain();
	}

	/**
	 * 维护时间结束后服务器设置为正常状态， 开服时间开始后服务器设置为正常状态
	 */
	private void _toNormal() {
		try {
			List<ServerInfo> serverList = serverInfoService.getServerList();
			long now = System.currentTimeMillis();
			for (ServerInfo serverInfo : serverList) {
				if (serverInfo.getStatus() == ServerInfo.STATUS_维护) {
					if (serverInfo.getMaintainEndTime() != null) {
						long maintainEndTime = serverInfo.getMaintainEndTime().getTime();
						if (now > maintainEndTime) {
							serverInfo.setStatus(ServerInfo.STATUS_正常);
							serverInfoService.updateServerInfo(serverInfo);
							ReloadData(serverInfo);
							logger.info("serverInfo------->：" + serverInfo);
						}
					}
				}

				if (serverInfo.getStatus() == ServerInfo.STATUS_即将开区) {
					if (serverInfo.getStartTime() != null) {
						long startTime = serverInfo.getStartTime().getTime();
						if (now > startTime) {
							serverInfo.setStatus(ServerInfo.STATUS_正常);
							serverInfoService.updateServerInfo(serverInfo);
							ReloadData(serverInfo);
							logger.info("serverInfo------->：" + serverInfo);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 更新服务器缓存
	 * 
	 * @param serverInfo
	 */
	private void ReloadData(ServerInfo serverInfo) {
		String worldIp = serverInfo.getWorldIp();
		String payNotifiUrl = serverInfo.getPayNotifiUrl();
		String port = payNotifiUrl.substring(payNotifiUrl.lastIndexOf(":"), payNotifiUrl.lastIndexOf("/"));
		String url = "http://" + worldIp + port + "/?method=reloadData";
		HttpUtil.getRet(url, "", "UTF-8", "UTF-8");
	}

	/**
	 * 维护时间开始后服务器设置为维护状态
	 */
	private void _toMaintain() {
		try {
			List<ServerInfo> serverList = serverInfoService.getServerList();
			for (ServerInfo serverInfo : serverList) {
				if (serverInfo.getStatus() == ServerInfo.STATUS_正常 || serverInfo.getStatus() == ServerInfo.STATUS_推荐  || serverInfo.getStatus() == ServerInfo.STATUS_火爆) {
					long now = System.currentTimeMillis();
					if (serverInfo.getMaintainStartTime() != null && serverInfo.getMaintainEndTime() != null) {
						long maintainStartTime = serverInfo.getMaintainStartTime().getTime();
						long maintainEndTime = serverInfo.getMaintainEndTime().getTime();
						if (now > maintainStartTime && now < maintainEndTime) {
							serverInfo.setStatus(ServerInfo.STATUS_维护);
							serverInfoService.updateServerInfo(serverInfo);
							ReloadData(serverInfo);
							logger.info("serverInfo------->：" + serverInfo);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
