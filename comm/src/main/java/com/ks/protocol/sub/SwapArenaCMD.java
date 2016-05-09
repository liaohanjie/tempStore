package com.ks.protocol.sub;
/**
 * 全局相关
 *
 */
public interface SwapArenaCMD {
	
	
	/**获取个人信息，及可挑战列表*/
	short GET_INFO = 1;

	/**挑战玩家*/
	short CHALLENGE = 2;
	
	/**获取战斗日志*/
	short GET_FIGHTLOG = 3;
	
	/**购买挑战次数*/
	short BUY_CHALLENGE_TIME = 4;
}
