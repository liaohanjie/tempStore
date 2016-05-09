package com.ks.model.logger;


public class ExpLogger extends GameLogger {

	private static final long serialVersionUID = 1L;
	
//	public static final int TYPE_战斗获得 = 1;
//	public static final int TYPE_扫荡获得 = 2;
//	public static final int TYPE_每日任务奖励= 3;
//	public static final int TYPE_收集兑换奖励=4;
//	public static final int TYPE_冲级活动 = 5;
//	public static final int TYPE_连续登陆活动 = 6;
//	public static final int TYPE_首充活动 = 7;
//	public static final int TYPE_累计充值活动 = 8;
//	public static final int TYPE_商城购买 = 9;
//	public static final int TYPE_冲榜赛活动 = 10;
//	public static final int TYPE_每日充值送豪礼 = 11;
//	public static final int TYPE_收集送礼 = 12;
//	public static final int TYPE_消费送礼 = 13;
//	public static final int TYPE_在线礼包 = 14;
//	public static final int TYPE_爬塔试炼 = 15;
//	public static final int TYPE_爬塔试炼集星 = 16;
	
	public static ExpLogger createExpLogger(int userId,int type,int num,int assId,String description){
		ExpLogger logger = new ExpLogger();
		logger.setLoggerType(GameLogger.LOGGER_TYPE_EXP);
		logger.setUserId(userId);
		logger.setType(type);
		logger.setNum(num);
		logger.setAssId(assId);
		logger.setDescription(description);
		return logger;
	}
}
