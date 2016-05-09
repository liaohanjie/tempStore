package com.ks.model.logger;

/**
 * 货币日志
 * @author ks
 */
public class CurrencyLogger extends GameLogger {

	private static final long serialVersionUID = 1L;
	
//	public static final int TYPE_战斗获得 = 1;
//	public static final int TYPE_增加战魂仓库容量 = 2;
//	public static final int TYPE_增加道具仓库容量 = 3;
//	public static final int TYPE_增加好友容量 = 4;
//	public static final int TYPE_购买体力 = 5;
//	public static final int TYPE_召唤消耗 = 6;
//	public static final int TYPE_公告获得 = 7;
//	public static final int TYPE_收取好友赠品 = 8;
//	public static final int TYPE_合成物品消耗 = 9;
//	public static final int TYPE_充值获得 = 10;
//	public static final int TYPE_成就奖励 = 11;
//	public static final int TYPE_购买竞技点 = 12;
//	public static final int TYPE_复活 = 13;
//	public static final int TYPE_新手赠送 = 14;
//	public static final int TYPE_购买扫荡次数 = 15;
//	public static final int TYPE_购买成长基金 = 16;
//	public static final int CODE_成长基金领取=17;
//	public static final int TYPE_扫荡获得 = 18;
//	public static final int TYPE_探索获得=19;
//	public static final int TYPE_购买限时礼包=20;
//	public static final int TYPE_每日任务奖励=21;
//	public static final int TYPE_收集兑换奖励=22;
//	public static final int TYPE_立即结束探索消耗=23;
//	public static final int TYPE_冲级活动 = 24;
//	public static final int TYPE_连续登陆活动 = 25;
//	public static final int TYPE_首充活动 = 26;
//	public static final int TYPE_累计充值活动 = 27;
//	public static final int TYPE_商城购买 = 28;
//	public static final int TYPE_冲榜赛活动 = 29;
//	public static final int TYPE_黄金月卡 = 30;
//	public static final int TYPE_钻石月卡 = 31;
//	public static final int TYPE_充值额外 = 32;
//	public static final int TYPE_点金手 = 33;
//	public static final int TYPE_每日充值送豪礼 = 34;
//	public static final int TYPE_收集送礼 = 35;
//	public static final int TYPE_消费送礼 = 36;
//	public static final int TYPE_在线礼包 = 37;
//	public static final int TYPE_购买爬塔次数 = 38;
//	public static final int TYPE_爬塔试炼 = 39;
//	public static final int TYPE_爬塔试炼集星 = 40;
//	public static final int TYPE_世界boss鼓舞 = 41;
//	public static final int TYPE_世界boss伤害奖励 = 42;
//	public static final int TYPE_世界boss击杀奖励 = 43;
//	public static final int TYPE_世界boss清除战斗cd = 44;
//	public static final int TYPE_世界boss参与奖励 = 45;
//	public static final int TYPE_世界boss排行奖励 = 46;
	
	public static CurrencyLogger createCurrencyLogger(int userId,int type,int num,int assId,String description){
		CurrencyLogger logger = new CurrencyLogger();
		logger.setLoggerType(GameLogger.LOGGER_TYPE_CURRENCY);
		logger.setUserId(userId);
		logger.setType(type);
		logger.setNum(num);
		logger.setAssId(assId);
		logger.setDescription(description);
		return logger;
	}
}
