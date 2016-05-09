package com.ks.action.logic;

import java.util.List;
import com.ks.protocol.vo.swaparena.BuyTimesResultVO;
import com.ks.protocol.vo.swaparena.SwapArenaFightLogVO;
import com.ks.protocol.vo.swaparena.ChallengeResultVO;
import com.ks.protocol.vo.swaparena.SwapArenaVO;

/**
 * 交换竞技场
 * @author hanjie.l
 *
 */
public interface SwapArenaAction {
	
	
	/**
	 * 获取竞技场信息
	 */
	public SwapArenaVO getSwapArenaInfo(int userId);
	
	/**
	 * 开始战斗
	 * @param userId 攻击者id
	 * @param targetId 攻击目标id
	 */
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
	public BuyTimesResultVO buyChallengeTimes(int userId, int count);
	
	/**
	 * 奖励排名靠前的玩家
	 */
	public void rewardTopPlayer();
}
