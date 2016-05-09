package com.ks.model.logger;

/**
 * 日志类型
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2015年12月24日
 */
public class LoggerType {
	
	public static int TYPE_高级账号 = 999;
	
	// athlet
	public static int 竞技点_type_自动回点=1001;
	public static int 竞技点_type_比赛扣点=1002;
	public static int 竞技点_type_花钱买点=1003;
	public static int 竞技点_type_第一次送竞技点=1004;
	public static int 竞技分数_type_胜负奖励=1005;
	
	// bak
	public static final int TYPE_进入副本=2001;
	public static final int TYPE_好友赠送=2002;
	public static final int TYPE_附件得到=2003;
	public static final int TYPE_战斗得到=2004;
	
	// currency
	public static final int TYPE_增加战魂仓库容量 = 3001;
	public static final int TYPE_增加道具仓库容量 = 3002;
	public static final int TYPE_增加好友容量 = 3003;
	public static final int TYPE_充值获得 = 3004;
	public static final int TYPE_购买竞技点 = 3005;
	public static final int TYPE_复活 = 3006;
	public static final int TYPE_购买成长基金 = 3007;
	public static final int CODE_成长基金领取=3008;
	public static final int TYPE_购买限时礼包=3009;
	public static final int TYPE_立即结束探索消耗=3010;
	public static final int TYPE_黄金月卡 = 3011;
	public static final int TYPE_钻石月卡 = 3012;
	public static final int TYPE_充值额外 = 3013;
	public static final int TYPE_购买爬塔次数 = 3014;
	public static final int TYPE_世界boss清除战斗cd = 3015;
	public static final int TYPE_交换竞技场购买挑战次数 = 3016;
	public static final int TYPE_刷新神秘商店= 3017;
	public static final int TYPE_创建工会 = 3018;
	public static final int TYPE_工会魂钻建设 = 3019;
	public static final int TYPE_订单返还 = 3020;
	
	// friend
	/**好友战斗*/
	public static final int TYPE_FRIEND_FIGHT = 4001;
	/**冒险者战斗*/
	public static final int TYPE_ADVENTURER_FIGHT = 4002;
	/**战斗获得*/
	public static final int TYPE_FIGHT = 4003;
	/**召唤消耗*/
	public static final int TYPE_召唤消耗 = 4004;
	public static final int TYPE_战斗固定产出 = 4005;

	// gold
	public static final int TYPE_战魂强化消耗 = 5001;
	public static final int TYPE_战魂进化消耗 = 5002;
	public static final int TYPE_战魂变异消耗 = 5003;
	public static final int TYPE_卖出战魂获得 = 5004;
	public static final int TYPE_战斗获得 = 5005;
	public static final int TYPE_卖出物品获得 = 5006;
	public static final int TYPE_建筑升级 = 5007;
	public static final int TYPE_公告获得 = 5008;
	public static final int TYPE_收取好友赠品 = 5009;
	public static final int TYPE_合成物品消耗 = 5010;
	public static final int TYPE_成就奖励 = 5011;
	public static final int TYPE_重塑战魂消耗 = 5012;
	public static final int TYPE_扫荡获得 = 5013;
	public static final int TYPE_探索消耗 = 5014;
	public static final int TYPE_探索获得=5015;
	public static final int TYPE_精炼装备消耗=5016;
	public static final int TYPE_每日任务奖励 = 5017;
	public static final int TYPE_收集兑换奖励=5018;
	public static final int TYPE_新手得到=5019;
	public static final int TYPE_冲级活动 = 5020;
	public static final int TYPE_连续登陆活动 = 5021;
	public static final int TYPE_首充活动 = 5022;
	public static final int TYPE_累计充值活动 = 5023;
	public static final int TYPE_商城购买 = 5024;
	public static final int TYPE_冲榜赛活动 = 5025;
	public static final int TYPE_点金手 = 5026;
	public static final int TYPE_每日充值送豪礼 = 5027;
	public static final int TYPE_收集送礼 = 5028;
	public static final int TYPE_消费送礼 = 5029;
	public static final int TYPE_在线礼包 = 5030;
	public static final int TYPE_爬塔试炼 = 5031;
	public static final int TYPE_爬塔试炼集星 = 5032;
	public static final int TYPE_世界boss鼓舞 = 5033;
	public static final int TYPE_世界boss伤害奖励 = 5034;
	public static final int TYPE_世界boss击杀奖励 = 5035;
	public static final int TYPE_世界boss参与奖励 = 5036;
	public static final int TYPE_世界boss排行奖励 = 5037;
	public static final int TYPE_工会金币建设 = 5038;
	public static final int TYPE_工会升级 = 5039;
	
	// goods
	public static final int TYPE_战斗道具放入背包 = 6001;
	public static final int TYPE_背包放入战斗道具 = 6002;
	public static final int TYPE_战斗消耗装备 = 6003;
	public static final int TYPE_战斗使用 = 6004;
	public static final int TYPE_复活消耗 = 6005;
	public static final int TYPE_卖出物品 = 6006;
	public static final int TYPE_建筑收集 = 6007;
	public static final int TYPE_合成物品获得 = 6008;
	public static final int TYPE_新手指引得到 = 6009;
	public static final int TYPE_创建账号= 6010;
	public static final int TYPE_工会捐赠材料= 6011;
	public static final int TYPE_工会商城= 6012;
	
	// soul
	public static final int TYPE_新手赠送 = 7001;
	public static final int TYPE_合成增加经验 = 7002;
	public static final int TYPE_合成消耗 = 7003;
	public static final int TYPE_进化消耗 = 7004;
	public static final int TYPE_出售战魂 = 7005;
	public static final int TYPE_召唤获得 = 7006;
	public static final int TYPE_重塑战魂得到 = 7007;
	public static final int TYPE_探索加经验 = 7008;
	public static final int TYPE_新手强化赠送 = 7009;
	public static final int TYPE_新手进化赠送 = 7010;
	public static final int TYPE_收集兑换消耗 = 7011;
	public static final int TYPE_新手奖励 = 7012;
	public static final int TYPE_VIP强化增加经验 = 7013;
	public static final int TYPE_机器初始化 = 7014;
	public static final int TYPE_工会捐赠战魂 = 7015;
	
	// stamina
	public static final int TYPE_战斗消耗 = 8001;
	public static final int TYPE_自动回体 = 8002;
	public static final int TYPE_购买体力 = 8003;
	public static final int TYPE_扫荡消耗 = 8004;
	public static final int TYPE_系统补偿 = 8005;
	public static final int TYPE_免费领取体力 = 8006;
	
	// sweep
	public static final int TYPE_购买扫荡次数 = 9001;
	public static final int TYPE_消耗扫荡次数= 9002;
	public static final int TYPE_购买副本战斗次数= 9003;
	
	public static final int TYPE_冒险宝箱 = 10001;
	public static final int TYPE_钥匙使用 = 10002;
	public static final int TYPE_神秘商店 = 10003;
	public static final int TYPE_每天登录送礼包 = 10004;
	public static final int TYPE_全民福利 = 10005;
	public static final int TYPE_七天送礼 = 10006;
	public static final int TYPE_七天额外奖励 = 10007;
	
}
