package com.ks.model.logger;

public class StaminaLogger extends GameLogger {

	private static final long serialVersionUID = 1L;
	
//	public static final int TYPE_战斗消耗 = 1;
//	public static final int TYPE_自动回体 = 2;
//	public static final int TYPE_购买体力 = 3;
//	public static final int TYPE_扫荡消耗 = 4;
//	public static final int TYPE_系统补偿 = 5;
//	public static final int TYPE_免费领取体力 = 6;
//	public static final int TYPE_冲级活动 = 7;
//	public static final int TYPE_连续登陆活动 = 8;
//	public static final int TYPE_首充活动 = 9;
//	public static final int TYPE_累计充值活动 = 10;
//	public static final int TYPE_商城购买 = 11;
//	public static final int TYPE_冲榜赛活动 = 12;
//	public static final int TYPE_每日充值送豪礼 = 13;
//	public static final int TYPE_收集送礼 = 14;
//	public static final int TYPE_消费送礼 = 15;
//	public static final int TYPE_在线礼包 = 16;
//	public static final int TYPE_爬塔试炼 = 17;
//	public static final int TYPE_爬塔试炼集星 = 18;
//	public static final int TYPE_世界boss伤害奖励 = 19;
//	public static final int TYPE_世界boss击杀奖励 = 20;
//	public static final int TYPE_世界boss参与奖励 = 21;
//	public static final int TYPE_世界boss排行奖励 = 22;
	
	public static StaminaLogger createStaminaLogger(int userId,int type,int num,int assId,String description){
		StaminaLogger logger = new StaminaLogger();
		logger.setLoggerType(GameLogger.LOGGER_TYPE_STAMINA);
		logger.setUserId(userId);
		logger.setType(type);
		logger.setNum(num);
		logger.setAssId(assId);
		logger.setDescription(description);
		return logger;
	}
}
