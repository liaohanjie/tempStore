package com.ks.protocol.sub;
/**
 * boss命令
 * @author hanjie.l
 *
 */
public interface BossCMD {
	
	/**获取活动信息*/
	short BOSS_获取活动信息 = 1;
	
	/**鼓舞*/
	short BOSS_鼓舞 = 2;
	
	/**开始战斗*/
	short BOSS_开始战斗 = 3;
	
	/**提交战斗*/
	short BOSS_提交战斗 = 4;
	
	/**清除战斗cd*/
	short BOSS_清除战斗cd = 5;
	
	/**领取奖励*/
	short BOSS_领取奖励 = 6;
	
}
