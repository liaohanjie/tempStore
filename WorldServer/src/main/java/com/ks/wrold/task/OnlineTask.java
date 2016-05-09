package com.ks.wrold.task;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.ks.action.logic.UserAction;
import com.ks.app.Application;
import com.ks.logger.LoggerFactory;
import com.ks.rpc.RPCKernel;
import com.ks.rpc.ServerInfo;
import com.ks.timer.task.BaseTask;
import com.ks.timer.task.Task;
import com.ks.wrold.kernel.WorldServerCache;

/**
 * 在线统计
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2016年1月25日
 */
@Task(initialDelay=60, period=60,unit=TimeUnit.SECONDS)
public class OnlineTask extends BaseTask {

	private Logger logger = LoggerFactory.get(OnlineTask.class);
	
	@Override
	public void runTask() {
		
		try {
			int num = 0;
			for (ServerInfo gameServer : WorldServerCache.getGameServerInfos()) {
				logger.info("online count serverId=" + gameServer.getServerId() + ", num=" + gameServer.getNum());
				num = num + gameServer.getNum();
			}
			UserAction action = RPCKernel.getRemoteByServerType(Application.LOGIC_SERVER, UserAction.class);
			
			if (action != null) {
				action.sendOnlinePlayerNum(Application.serverId, num);
				logger.info("all online count serverId=" + Application.serverId + ", num=" + num);
			} else {
				logger.warn("game server haven't started");
			}
		} catch (Exception e) {
			logger.warn("game server haven't started");
		}
		
		//testMarquee();
	}
	
	/*static int i = 0;
	
	public void testMarquee(){
		long start = System.currentTimeMillis();
		logger.info("跑马灯测试开始: size=" + MarqueeCache.getMarqueeList().size());
		try {
			MarqueeCache.add(createSoulMarquee("大黄" + (i++), "大黄蜂" + (i++), 5 + i));
			MarqueeCache.add(createEquipmentMarquee("大黄" + (i++), "开山斧" + (i++)));
			MarqueeCache.add(createBossMarquee("大黄" + (i++), "地狱魔王" + (i++), 10 + i));
		} catch(Exception e) {
			logger.warn("", e);
		}
		logger.info("跑马灯测试结束:cost=" + (System.currentTimeMillis() - start) + ", size=" + MarqueeCache.getMarqueeList().size());
	}
	
	static String FORMAT_BASE = "<font color=%s>%s</font>";
	static String COLOR_PLAYER = "lblue";
	static String COLOR_EQUIPMENT = "lyellow";
	static String COLOR_BOSS = "lred";
	static String COLOR_SOUL = "lpurple";
	static String COLOR_MSG = "lgreen";
	
	public static MarqueeMsg createSoulMarquee(String playerName, String soulName, int rare) {
		MarqueeMsg entity = new MarqueeMsg();
		playerName = _getPlayerName(playerName);
		soulName = String.format(FORMAT_BASE, COLOR_SOUL, soulName);
		entity.setContent(String.format(FORMAT_BASE, COLOR_MSG, String.format("%s刚刚获得了%s星战魂%s,在冒险的道路上又增加了一名强有力的伙伴", playerName, rare, soulName)));
		entity.setCreateTime(new Date());
		return entity;
	}
	
	public static MarqueeMsg createEquipmentMarquee(String playerName, String equipmentName) {
		MarqueeMsg entity = new MarqueeMsg();
		playerName = _getPlayerName(playerName);
		equipmentName = String.format(FORMAT_BASE, COLOR_EQUIPMENT, equipmentName);
		entity.setContent(String.format(FORMAT_BASE, COLOR_MSG, String.format("通过一次次锲而不舍的战斗,%s玩家终于获得了%s", playerName, equipmentName)));
		entity.setCreateTime(new Date());
		return entity;
	}
	
	public static MarqueeMsg createBossMarquee(String playerName, String monsterName, int level) {
		MarqueeMsg entity = new MarqueeMsg();
		playerName = _getPlayerName(playerName);
		monsterName = String.format(FORMAT_BASE, COLOR_BOSS, monsterName);
		entity.setContent(String.format(FORMAT_BASE, COLOR_MSG, String.format("%s玩家经过不懈的努力终于斩获%s等级%s世界BOSS", playerName, level, monsterName)));
		entity.setCreateTime(new Date());
		return entity;
	}
	
	private static String _getPlayerName(String name){
		return String.format(FORMAT_BASE, COLOR_PLAYER, name);
	}*/
}
