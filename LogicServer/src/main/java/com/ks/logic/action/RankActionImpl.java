package com.ks.logic.action;

import com.ks.action.logic.RankAction;
import com.ks.logic.service.RankService;
import com.ks.logic.service.ServiceFactory;
import com.ks.protocol.vo.rank.RankInfoVO;

public class RankActionImpl implements RankAction {
	
	private static RankService rankService = ServiceFactory.getService(RankService.class);

	@Override
	public RankInfoVO getRankInfo(int userId, int rankTypeId) {
		return rankService.getRankInfo(userId, rankTypeId);
	}

}
