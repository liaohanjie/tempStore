package com.ks.protocol.sub;
/**
 * 用户命令
 * @author ks
 */
public interface UserCMD {
	
	/**获取用户数据*/
	short GAIN_USER_INFO = 1;
	/**心跳*/
	short USER_HEART = 2;
	/**获取用户统计*/
	short GAIN_USER_STAT = 3;
	/**恢复体力*/
	short REGAIN_STAMINA = 4;
	/**取名*/
	short USER_取名 = 5;
	/**选择战魂*/
	short USER_选择战魂= 6;
	/**引导下一步*/
	short USER_引导下一步 = 7;
	/**用户BUFF*/
	short USER_用户_BUFF = 8;
	/**购买扫荡次数*/
	short USER_购买扫荡次数 = 9;
	/**购买基金*/
	short USER_购买基金 = 10;
	/**领取基金奖励*/
	short USER_领取基金奖励 = 11;
	/**引导下一步剧情*/
	short USER_剧情副本 = 12;
	/**家园引导（非强制）下一步*/
	short USER_非强制引导下一步= 13;
	/**配置信息*/
	short USER_CONFIG= 14;
	/**免费领取体力*/
	short USER_SEND_STAMAIN= 15;
	/**用户排行*/
	short USER_RANK = 16;
	/**点金手*/
	short COIN_HAND = 17;

}
