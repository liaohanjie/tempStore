package com.ks.model.logger;

public class BakPropLogger extends GameLogger {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
//	public static final int BAK_PROP_LOG_TYPE_进入副本=1;
//	public static final int BAK_PROP_LOG_TYPE_好友赠送=2;
//	public static final int BAK_PROP_LOG_TYPE_附件得到=3;
//	public static final int BAK_PROP_LOG_TYPE_战斗得到=4;
//	public static final int BAK_PROP_LOG_TYPE_每日任务奖励=5;
//	public static final int BAK_PROP_LOG_TYPE_收集兑换奖励=6;
//	public static final int BAK_PROP_LOG_TYPE_成就奖励=7;
//	public static final int BAK_PROP_LOG_TYPE_冲级活动 = 8;
//	public static final int BAK_PROP_LOG_TYPE_连续登陆活动 = 9;
//	public static final int BAK_PROP_LOG_TYPE_首充活动 = 10;
//	public static final int BAK_PROP_LOG_TYPE_累计充值活动 = 11;
//	public static final int BAK_PROP_LOG_TYPE_商城购买 = 12;
//	public static final int BAK_PROP_LOG_TYPE_冲榜赛活动 = 13;
//	public static final int BAK_PROP_LOG_TYPE_每日充值送豪礼 = 14;
//	public static final int BAK_PROP_LOG_TYPE_收集送礼 = 15;
//	public static final int BAK_PROP_LOG_TYPE_消费送礼 = 16;
//	public static final int BAK_PROP_LOG_TYPE_在线礼包 = 17;
//	public static final int BAK_PROP_LOG_TYPE_爬塔试炼 = 18;
//	public static final int BAK_PROP_LOG_TYPE_爬塔试炼集星 = 19;
//	public static final int TYPE_世界boss伤害奖励 = 20;
//	public static final int TYPE_世界boss击杀奖励 = 21;
//	public static final int TYPE_世界boss参与奖励 = 22;
//	public static final int TYPE_世界boss排行奖励 = 23;
	
	public static BakPropLogger createBakPropLogger(int userId,int type,int num,int assId,String des){
		BakPropLogger logger = new BakPropLogger();
		logger.setLoggerType(GameLogger.LOGGER_TYPE_BAK_PROP);
		logger.setUserId(userId);
		logger.setType(type);
		logger.setNum(num);
		logger.setAssId(assId);
		logger.setDescription(des);
		return logger;
	}

}
