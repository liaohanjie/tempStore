package com.ks.protocol.sub;
/**
 * 用户战魂子命令
 * @author ks
 */
public interface UserSoulCMD {
	
	/**强化战魂*/
	short STRENG_USER_SOUL = 1;
	/**进化战魂*/
	short SOUL_EVOLUTION = 2;
	/**卖出战魂*/
	short SELL_SOUL = 3;
	/**召唤战魂*/
	short CALL_SOUL = 4;
	/**获得战魂信息*/
	short GAIN_SOUL_INFO = 5;
	/**增加战魂仓库容量*/
	short ADD_SOUL_CAPACITY = 6;
	
	short SOUL_重塑战魂 = 7;
	
	/**修改战魂保护状态*/
	short UPDATE_SOUL_SAFE = 8;
	
	short SOUL_领取强化素材 = 9;
	
	short SOUL_新手进化战魂= 10;
}
