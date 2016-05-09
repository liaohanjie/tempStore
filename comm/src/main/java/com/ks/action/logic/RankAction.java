package com.ks.action.logic;

import com.ks.protocol.vo.rank.RankInfoVO;

public interface RankAction {
	
	/**
	 * 获取排行榜
	 * @param userId
	 * @param rankTypeId
	 * (1: 等级排行， 2：推图（章节排行） 3: boss排行榜   4: 爬塔)
	 */
	public RankInfoVO getRankInfo(int userId, int rankTypeId);
}
