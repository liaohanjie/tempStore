package com.ks.logic.dao.opt;

public class UserStatOpt extends SQLOpt {
	
	/**友情点*/
	public byte friendlyPoint;
	/**使用过的好友*/
	public byte useFriend;
	/**赠送过礼物的好友*/
	public byte handselFriend;
	/**购买体力*/
	public byte buyStaminaCount;
	
	/**连续登录*/
	public byte continueLogin;
	/**累计有登录*/
	public byte totalLogin;	
	/**已领取的登录奖励*/
	public byte onTimeLoginAward;	
	/**已领取的连续奖励*/
	public byte continueLoginAward;
	/**已领取的累计登录奖励*/
	public byte totalLoginAward;
	/**vip已领奖时间*/
	public byte vipAwardTime;
	/**vip已扫荡次数*/
	public byte sweepCount;
	/**vip已购买扫荡次数*/
	public byte buySweepCount;
	/**今日已扫荡次数*/
	public byte chapterJoinCount;
	/**成长基金已领取奖励*/
	public byte growthGift;
	/**onTimeGiftTime*/
	public byte onTimeGiftTime;
	/**活动充值总数*/
	public byte acChargeTotal;
	/**扫荡的获得得战魂个数*/
	public byte sweepSoulNum;
	/**扫荡的获得得战魂个数*/
	public byte refreshMissionDate;
	/**今天已经合作胜利的好友*/
	public byte winFriend;
	/**每天免费领取体力次数*/
	public byte sendStamainCount;
	/**立即结束探索次数*/
	public byte buyExploretionCount;
	/**连续登陆次数礼包领取标示*/
	public byte continuousLoginGiftMark;
	/**冲级礼包领取标示*/
	public byte levelGiftMark;
	/**商品购买每天记录*/
	public byte productDayMark;
	/**限时活动充值(单位：魂钻)*/
	public byte activityRechargeCurrency;
	/**点金手当天已使用次数*/
	public byte coinHandNum;
	/**当天复活次数*/
	//public byte resurrectionNum;
	/**友情点召唤次数*/
	public byte callSoulFriendPointNum;
	/**魂钻召唤次数*/
	public byte callSoulCurrencyNum;
	/**每日充值累计*/
	public byte dayTotalCurrency;
	/**活动内连续充值次数(连续充值活动,按天算连续)*/
	public byte activityContinuousRechargeCount;
	/**最后充值时间*/
	public byte lastRechargeTime;
	/**活动内累计消费额*/
	public byte activityTotalCostCurrency;
	/**在线礼包领取次数(按天)*/
	public byte onlineGiftGetCount;
	/**活动连续领取记录(二进制标记)*/
	public byte activityContinuousRechargeGetMark;
	/**免费战魂召唤时间*/
	public byte freeCallSoulTime;
	/**神秘商店最后刷新时间*/
	public byte mysteryShopFreshTime;
}
