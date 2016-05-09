package com.ks.logic.service;

import com.ks.protocol.vo.rank.RankInfoVO;

/**
 * 排行榜
 * @author hanjie.l
 *
 */
public interface RankService {

	/**
	 * 获取排行榜
	 * @param userId
	 * @param rankTypeId
	 * (1: 等级排行， 2：推图（章节排行） 3: boss排行榜   4: 爬塔       5:交换竞技场)
	 */
	public RankInfoVO getRankInfo(int userId, int rankTypeId);
}
