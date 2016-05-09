package com.ks.logic.action;

import java.util.List;

import com.ks.action.logic.SwapArenaAction;
import com.ks.logic.service.ServiceFactory;
import com.ks.logic.service.SwapArenaService;
import com.ks.protocol.vo.swaparena.BuyTimesResultVO;
import com.ks.protocol.vo.swaparena.SwapArenaFightLogVO;
import com.ks.protocol.vo.swaparena.ChallengeResultVO;
import com.ks.protocol.vo.swaparena.SwapArenaVO;

/**
 * 交换竞技场
 * @author hanjie.l
 *
 */
public class SwapArenaActionImpl implements SwapArenaAction {
	
	private static final SwapArenaService swapArenaService = ServiceFactory.getService(SwapArenaService.class);

	@Override
	public SwapArenaVO getSwapArenaInfo(int userId) {
		return swapArenaService.getSwapArenaInfo(userId);
	}

	@Override
	public ChallengeResultVO challenge(int userId, int targetId) {
		return swapArenaService.challenge(userId, targetId);
	}

	@Override
	public List<SwapArenaFightLogVO> getFightLog(int userId) {
		return swapArenaService.getFightLog(userId);
	}

	@Override
	public void rewardTopPlayer() {
		swapArenaService.rewardTopPlayer();
	}

	@Override
	public BuyTimesResultVO buyChallengeTimes(int userId, int count) {
		return swapArenaService.buyChallengeTimes(userId, count);
	}
}
