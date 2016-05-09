package com.ks.logic.service;

import java.util.List;

import com.ks.access.Transaction;
import com.ks.protocol.vo.rank.RankNoticeVO;
import com.ks.protocol.vo.swaparena.BuyTimesResultVO;
import com.ks.protocol.vo.swaparena.SwapArenaFightLogVO;
import com.ks.protocol.vo.swaparena.ChallengeResultVO;
import com.ks.protocol.vo.swaparena.SwapArenaVO;

/**
 * 交换竞技场
 * @author hanjie.l
 *
 */
public interface SwapArenaService {
	
	
	/**
	 * 获取竞技场信息
	 */
	@Transaction
	public SwapArenaVO getSwapArenaInfo(int userId);
	
	
	/**
	 * 开始战斗
	 * @param userId 攻击者id
	 * @param targetId 攻击目标id
	 */
	@Transaction
	public ChallengeResultVO challenge(int userId, int targetId);
	
	/**
	 * 查看战斗日志
	 * @param userId
	 */
	public List<SwapArenaFightLogVO> getFightLog(int userId);
	
	/**
	 * 购买挑战次数
	 * @param userId
	 */
	@Transaction
	public BuyTimesResultVO buyChallengeTimes(int userId, int count);
	
	
	/**
	 * 奖励排名靠前的玩家
	 */
	@Transaction
	public void rewardTopPlayer();
	
	/**
	 * 获取排行榜公告信息
	 * @param userId
	 * @return
	 */
	public RankNoticeVO getSwapArenaRankNotice(int userId);

}
