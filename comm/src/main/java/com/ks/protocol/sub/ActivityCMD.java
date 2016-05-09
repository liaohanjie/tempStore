package com.ks.protocol.sub;
/**
 * 活动子命令
 * @author living.li
 * @date   2014年6月23日
 */
public interface ActivityCMD  {
	/**活动信息*/
	short ACTIVITY_取得活动信息 = 1;
	/**我的充值活动信息*/
	short ACTIVITY_我的充值活动信息 = 2;
	/**限时礼包信息*/
	short ACTIVITY_限量礼包信息 = 3;
	/**活动期间打折价格信息*/
	short ACTIVITY_打折价格 = 4;
	/**招魂出指定的战魂*/
	short ACTIVITY_招魂出指定的战魂 = 6;
	/**概率活动翻倍信息*/
	short ACTIVITY_概率活动翻倍信息 = 7;
	/**活动礼包获取使用状态*/
	short ACTIVITY_GIFT_信息 = 8;
	/**活动礼包获取*/
	short ACTIVITY_GIFT_领取 = 9;
	/**多个活动礼包获取使用状态*/
	short ACTIVITY_GIFT_LIST_信息 = 10;
}