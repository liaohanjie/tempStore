package com.ks.model.logger;

/**
 * 战魂日志
 * 
 * @author ks
 */
public class SoulLogger extends GameLogger {

	private static final long serialVersionUID = 1L;

//	public static final int TYPE_新手赠送 = 1;
//	public static final int TYPE_合成增加经验 = 2;
//	public static final int TYPE_合成消耗 = 3;
//	public static final int TYPE_进化消耗 = 4;
//	public static final int TYPE_战魂变异消耗 = 5;
//	public static final int TYPE_出售战魂 = 6;
//	public static final int TYPE_战斗获得 = 7;
//	public static final int TYPE_召唤获得 = 8;
//	public static final int TYPE_公告获得 = 9;
//	public static final int TYPE_成就奖励 = 10;
//	public static final int TYPE_重塑战魂消耗 = 11;
//	public static final int TYPE_重塑战魂得到 = 12;
//	public static final int TYPE_扫荡获得 = 13;
//	public static final int TYPE_探索加经验 = 14;
//	public static final int TYPE_探索获得 = 15;
//	public static final int TYPE_新手强化赠送 = 16;
//	public static final int TYPE_新手进化赠送 = 17;
//	public static final int TYPE_每日任务奖励 = 18;
//	public static final int TYPE_收集兑换奖励 = 19;
//	public static final int TYPE_收集兑换消耗 = 20;
//	public static final int TYPE_新手奖励 = 21;
//	public static final int TYPE_冲级活动 = 22;
//	public static final int TYPE_连续登陆活动 = 23;
//	public static final int TYPE_首充活动 = 24;
//	public static final int TYPE_累计充值活动 = 25;
//	public static final int TYPE_商城购买 = 26;
//	public static final int TYPE_冲榜赛活动 = 27;
//	public static final int TYPE_VIP强化增加经验 = 28;
//	public static final int TYPE_每日充值送豪礼 = 29;
//	public static final int TYPE_收集送礼 = 30;
//	public static final int TYPE_消费送礼 = 31;
//	public static final int TYPE_在线礼包 = 32;
//	public static final int TYPE_爬塔试炼 = 33;
//	public static final int TYPE_爬塔试炼集星 = 34;
//	public static final int TYPE_世界boss伤害奖励 = 35;
//	public static final int TYPE_世界boss击杀奖励 = 36;
//	public static final int TYPE_世界boss参与奖励 = 37;
//	public static final int TYPE_世界boss排行奖励 = 38;

	public static SoulLogger createSoulLogger(int userId, int type, int num, int assId, String description) {
		SoulLogger logger = new SoulLogger();
		logger.setLoggerType(GameLogger.LOGGER_TYPE_SOUL);
		logger.setUserId(userId);
		logger.setType(type);
		logger.setNum(num);
		logger.setAssId(assId);
		logger.setDescription(description);
		return logger;
	}
}
