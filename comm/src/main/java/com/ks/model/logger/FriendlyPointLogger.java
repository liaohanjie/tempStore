package com.ks.model.logger;
/**
 * 友情点日志
 * @author ks
 */
public class FriendlyPointLogger extends GameLogger {

	private static final long serialVersionUID = 1L;
//	/**好友战斗*/
//	public static final int TYPE_FRIEND_FIGHT = 1;
//	/**冒险者战斗*/
//	public static final int TYPE_ADVENTURER_FIGHT = 2;
//	/**战斗获得*/
//	public static final int TYPE_FIGHT = 3;
//	/**召唤消耗*/
//	public static final int TYPE_召唤消耗 = 4;
//	public static final int TYPE_战斗获得 = 5;
//	public static final int TYPE_公告获得 = 6;
//	public static final int TYPE_收取好友赠品 = 7;
//	public static final int TYPE_合成物品消耗 = 8;
//	public static final int TYPE_成就奖励 = 9;
//	public static final int TYPE_战斗固定产出 = 10;
//	public static final int TYPE_探索获得=11;
//	public static final int TYPE_每日任务奖励 = 12;
//	public static final int TYPE_收集兑换奖励 = 13;
//	public static final int TYPE_冲级活动 = 14;
//	public static final int TYPE_连续登陆活动 = 15;
//	public static final int TYPE_首充活动 = 16;
//	public static final int TYPE_累计充值活动 = 17;
//	public static final int TYPE_商城购买 = 18;
//	public static final int TYPE_冲榜赛活动 = 19;
//	public static final int TYPE_每日充值送豪礼 = 20;
//	public static final int TYPE_收集送礼 = 21;
//	public static final int TYPE_消费送礼 = 22;
//	public static final int TYPE_在线礼包 = 23;
//	public static final int TYPE_爬塔试炼 = 24;
//	public static final int TYPE_爬塔试炼集星 = 25;
//	public static final int TYPE_世界boss伤害奖励 = 26;
//	public static final int TYPE_世界boss击杀奖励 = 27;
//	public static final int TYPE_世界boss参与奖励 = 28;
//	public static final int TYPE_世界boss排行奖励 = 29;
	
	public static FriendlyPointLogger createFriendlyPointLogger(int userId,int type,int num,int assId,String description){
		FriendlyPointLogger logger = new FriendlyPointLogger();
		logger.setLoggerType(GameLogger.LOGGER_TYPE_FRIENDLY_POINT);
		logger.setUserId(userId);
		logger.setType(type);
		logger.setNum(num);
		logger.setAssId(assId);
		logger.setDescription(description);
		return logger;
	}
}
