package com.ks.action.logic;

import java.util.List;

import com.ks.model.boss.CheckBossOpenResult;
import com.ks.protocol.vo.boss.BossFightEndResultVO;
import com.ks.protocol.vo.boss.BossFightStartResultVO;
import com.ks.protocol.vo.boss.MoneyChangeVO;
import com.ks.protocol.vo.boss.WorldBossRecordVO;
import com.ks.protocol.vo.goods.GainAwardVO;

/**
 * 世界boss
 * @author hanjie.l
 *
 */
public interface BossAction {

	/**
	 * 获取所有boss的开启状态(内部调用)
	 * @return
	 */
	public List<CheckBossOpenResult> getCheckBossOpenResult();
	
	/**
	 * 初始化boss(内部调用)
	 * @param bossId
	 * @param version
	 */
	public void initWorldBoss(int bossId, String version, long endTime);
	
	/**
	 * 结束boss活动(内部调用)
	 * @param bossId
	 */
	public void destroyWorldBoss(int bossId, long nextBeginTime);
	
	//=================================================================

	/**
	 * 获取boss信息
	 * 
	 * @return
	 */
	public List<WorldBossRecordVO> getBossInfo(int userId);

	/**
	 * 鼓舞
	 * 
	 * @param bossId
	 * @param type
	 *            1金币鼓舞 2魂钻鼓舞
	 */
	public MoneyChangeVO inspired(int userId, int bossId, byte type);

	/**
	 * 开始战斗
	 * 
	 * @param teamId
	 *            队伍id
	 * @param bossId
	 * @throws Exception
	 */
	public BossFightStartResultVO startFight(int userId, byte teamId, int bossId);

	/**
	 * 提交战斗
	 * 
	 * @param bossId
	 * @param hurt
	 */
	public BossFightEndResultVO endFight(int userId, int bossId, long hurt);

	/**
	 * 清除战斗cd
	 * 
	 * @param handler
	 * @param bossId
	 */
	public MoneyChangeVO clearFightCd(int userId, int bossId);
	
	/**
	 * 领取参与奖励
	 * @param userId
	 * @return
	 */
	public GainAwardVO getBossJoinRewards(int userId);
	
	/**
	 * 领取排名奖励
	 * @param userId
	 * @return
	 */
	public GainAwardVO getBossRankRewards(int userId);
}
