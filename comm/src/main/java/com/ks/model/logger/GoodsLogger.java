package com.ks.model.logger;
/**
 * 物品日志
 * @author ks
 */
public class GoodsLogger extends GameLogger {

	private static final long serialVersionUID = 1L;
//	public static final int TYPE_战斗获得 = 1;
//	public static final int TYPE_战斗道具放入背包 = 2;
//	public static final int TYPE_背包放入战斗道具 = 3;
//	public static final int TYPE_战斗消耗装备 = 4;
//	public static final int TYPE_战斗使用 = 5;
//	public static final int TYPE_复活消耗 = 6;
//	public static final int TYPE_卖出物品 = 7;
//	public static final int TYPE_建筑收集 = 8;
//	public static final int TYPE_公告获得 = 9;
//	public static final int TYPE_收取好友赠品 = 10;
//	public static final int TYPE_合成物品消耗 = 11;
//	public static final int TYPE_合成物品获得 = 12;
//	public static final int TYPE_成就奖励 = 13;
//	public static final int TYPE_新手指引得到 = 14;
//	public static final int TYPE_创建账号= 15;
//	public static final int TYPE_探索获得= 16;
//	public static final int TYPE_扫荡获得 = 17;
//	public static final int TYPE_精炼装备消耗 = 18;
//	public static final int TYPE_每日任务奖励=19;
//	public static final int TYPE_收集兑换奖励=20;
//	public static final int TYPE_收集兑换消耗 = 21;
//	public static final int TYPE_战魂进化消耗= 22;
//	public static final int TYPE_冲级活动 = 23;
//	public static final int TYPE_连续登陆活动 = 24;
//	public static final int TYPE_首充活动 = 25;
//	public static final int TYPE_累计充值活动 = 26;
//	public static final int TYPE_商城购买 = 27;
//	public static final int TYPE_冲榜赛活动 = 28;
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
	
	public static GoodsLogger createPropLogger(int userId,int type,int num,int assId,String description){
		GoodsLogger logger = new GoodsLogger();
		logger.setLoggerType(GameLogger.LOGGER_TYPE_PROP);
		logger.setUserId(userId);
		logger.setType(type);
		logger.setNum(num);
		logger.setAssId(assId);
		logger.setDescription(description);
		return logger;
	}
	public static GoodsLogger createEquipmentLogger(int userId,int type,int num,int assId,String description){
		GoodsLogger logger = new GoodsLogger();
		logger.setLoggerType(GameLogger.LOGGER_TYPE_EQUIPMENT);
		logger.setUserId(userId);
		logger.setType(type);
		logger.setNum(num);
		logger.setAssId(assId);
		logger.setDescription(description);
		return logger;
	}
	public static GoodsLogger createStuffLogger(int userId,int type,int num,int assId,String description){
		GoodsLogger logger = new GoodsLogger();
		logger.setLoggerType(GameLogger.LOGGER_TYPE_STUFF);
		logger.setUserId(userId);
		logger.setType(type);
		logger.setNum(num);
		logger.setAssId(assId);
		logger.setDescription(description);
		return logger;
	}
}
