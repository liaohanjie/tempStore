package com.ks.model.logger;
/**
 * 金币日志
 * @author ks
 */
public class GoldLogger extends GameLogger {

	private static final long serialVersionUID = 1L;
	
//	public static final int TYPE_战魂强化消耗 = 1;
//	public static final int TYPE_战魂进化消耗 = 2;
//	public static final int TYPE_战魂变异消耗 = 3;
//	public static final int TYPE_卖出战魂获得 = 4;
//	public static final int TYPE_战斗获得 = 5;
//	public static final int TYPE_卖出物品获得 = 6;
//	public static final int TYPE_建筑升级 = 7;
//	public static final int TYPE_公告获得 = 8;
//	public static final int TYPE_收取好友赠品 = 9;
//	public static final int TYPE_合成物品消耗 = 10;
//	public static final int TYPE_成就奖励 = 11;
//	public static final int TYPE_重塑战魂消耗 = 12;
//	public static final int TYPE_扫荡获得 = 13;
//	public static final int TYPE_探索消耗 = 14;
//	public static final int TYPE_探索获得=15;
//	public static final int TYPE_精炼装备消耗=16;
//	public static final int TYPE_每日任务奖励 = 17;
//	public static final int TYPE_收集兑换奖励=18;
//	public static final int TYPE_新手得到=19;
//	public static final int TYPE_冲级活动 = 20;
//	public static final int TYPE_连续登陆活动 = 21;
//	public static final int TYPE_首充活动 = 22;
//	public static final int TYPE_累计充值活动 = 23;
//	public static final int TYPE_商城购买 = 24;
//	public static final int TYPE_冲榜赛活动 = 25;
//	public static final int TYPE_点金手 = 26;
//	public static final int TYPE_每日充值送豪礼 = 27;
//	public static final int TYPE_收集送礼 = 28;
//	public static final int TYPE_消费送礼 = 29;
//	public static final int TYPE_在线礼包 = 30;
//	public static final int TYPE_爬塔试炼 = 31;
//	public static final int TYPE_爬塔试炼集星 = 32;
//	public static final int TYPE_世界boss鼓舞 = 33;
//	public static final int TYPE_世界boss伤害奖励 = 34;
//	public static final int TYPE_世界boss击杀奖励 = 35;
//	public static final int TYPE_世界boss参与奖励 = 36;
//	public static final int TYPE_世界boss排行奖励 = 37;
	
	public static GoldLogger createGoldLogger(int userId,int type,int num,int assId,String description){
		GoldLogger logger = new GoldLogger();
		logger.setLoggerType(GameLogger.LOGGER_TYPE_GOLD);
		logger.setUserId(userId);
		logger.setType(type);
		logger.setNum(num);
		logger.setAssId(assId);
		logger.setDescription(description);
		return logger;
	}
}
