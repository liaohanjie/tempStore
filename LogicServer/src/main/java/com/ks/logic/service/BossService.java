package com.ks.logic.service;

import java.util.List;

import com.ks.access.Transaction;
import com.ks.model.boss.BossOpenSetting;
import com.ks.model.boss.BossSetting;
import com.ks.model.boss.BossrankRewardSetting;
import com.ks.model.boss.CheckBossOpenResult;
import com.ks.protocol.vo.boss.BossFightEndResultVO;
import com.ks.protocol.vo.boss.BossFightStartResultVO;
import com.ks.protocol.vo.boss.MoneyChangeVO;
import com.ks.protocol.vo.boss.WorldBossRecordVO;
import com.ks.protocol.vo.goods.GainAwardVO;
import com.ks.protocol.vo.rank.RankNoticeVO;

/**
 * boss活动服务
 * 
 * @author hanjie.l
 * 
 */
public interface BossService {
	
	
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
	@Transaction
	public void initWorldBoss(int bossId, String version, long endTime);
	
	/**
	 * 结束boss活动(内部调用)
	 * @param bossId
	 */
	@Transaction
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
	@Transaction
	public MoneyChangeVO inspired(int userId, int bossId, byte type);

	/**
	 * 开始战斗
	 * 
	 * @param teamId
	 *            队伍id
	 * @param bossId
	 * @throws Exception
	 */
	@Transaction
	public BossFightStartResultVO startFight(int userId, byte teamId, int bossId);

	/**
	 * 提交战斗
	 * 
	 * @param bossId
	 * @param hurt
	 */
	@Transaction
	public BossFightEndResultVO endFight(int userId, int bossId, long hurt);

	/**
	 * 清除战斗cd
	 * 
	 * @param handler
	 * @param bossId
	 */
	@Transaction
	public MoneyChangeVO clearFightCd(int userId, int bossId);
	
	/**
	 * 获取boss排行榜公告信息
	 * @param userId
	 * @return
	 */
	public RankNoticeVO getBossRankNotice(int userId);
	
	/**
	 * 领取参与奖励
	 * @param userId
	 * @return
	 */
	@Transaction
	public GainAwardVO getBossJoinRewards(int userId);
	
	/**
	 * 领取排名奖励
	 * @param userId
	 * @return
	 */
	@Transaction
	public GainAwardVO getBossRankRewards(int userId);
	
	//===================================================================

	public List<BossSetting> getAllBossSetting();

	public List<BossOpenSetting> getAllBossOpenSetting();

	public List<BossrankRewardSetting> getAllBossRankRewardSetting();

}
