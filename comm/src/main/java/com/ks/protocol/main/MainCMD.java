package com.ks.protocol.main;
/**
 * 
 * @author ks.wu
 * @date 2014年7月11日
 */
public interface MainCMD {
	/**异常码主命令*/
	short ERROR_CODE = 0;
	/**登录协议 主命令*/
	short LOGIN = 1;
	/**用户协议主命令*/
	short USER = 2;
	/**副本主命令*/
	short CHAPTER = 3;
	/**用户战魂*/
	short USER_SOUL = 4;
	/**用户队伍*/
	short USER_TEAM = 5;
	/**用户物品*/
	short USER_GOODS = 6;
	/**好友*/
	short FRIEND = 7;
	/**建筑*/
	short BUDING = 8;
	/**竞技场*/
	short ARENA = 9;
	/**邮件*/
	short AFFICHE = 10;	
	/**图鉴*/
	short USER_SOUL_MAP = 11;	
	/**用户成就*/
	short USER_用户成就= 12;
	/**活动*/
	short MAIN_活动= 13;
	/**PVP战斗*/
	short PVP_ATHLETICSINFO_战斗=14;
	/**探索*/
	short MAIN_探索=15;
	
	/**每日任务*/
	short USER_MISSION=16;
	
	/**商城*/
	short SHOP = 17;
	
	/**世界boss*/
	short WORLDBOSS = 18;
	
	/**排行榜*/
	short Rank = 19;
	
	/**爬塔试炼*/
	short CLIMB_TOWER = 20;
	
	/**聊天*/
	short CHAT = 21;
	
	/**全局相关*/
	short GLOBAL = 22;
	
	/**交换竞技场*/
	short SWAPARENA = 23;
	
	/**战斗校验*/
	short CHECKFIGHT = 24;
	
	/**战斗校验*/
	short ALLIANCE = 25;
	
	/**公告*/
	short NOTICE = 26;
}
