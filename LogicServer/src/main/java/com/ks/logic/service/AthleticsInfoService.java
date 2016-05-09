package com.ks.logic.service;

import java.util.List;

import com.ks.access.Transaction;
import com.ks.model.pvp.AthleticsInfo;
import com.ks.model.pvp.AthleticsInfoAward;
import com.ks.protocol.vo.pvp.AthleticsInfoModelVO;
import com.ks.protocol.vo.pvp.AthleticsInfoVO;

public interface AthleticsInfoService {
	/**
	 * 恢复竞技点
	 * @param info
	 */
	@Transaction
	public int regainAthleticsPoint(int userId);
	
	/**
	 * 查询积分前三十名
	 * @return
	 */
	public AthleticsInfoModelVO queryAthleticsInfoBytotalIntegral(int userId);
	
	/**
	 * 匹配对手
	 * @return
	 */
	@Transaction
	public AthleticsInfoVO queryMatchUserIds(int userId);
	
	/**
	 * 进入竞技场
	 * @return
	 */
	@Transaction
	public AthleticsInfoVO enterArnea(int userId);
	
	/**
	 * 开始PK
	 * @return
	 */
	@Transaction
	public AthleticsInfoVO startArneaPK(int attackerId, int defenderId);
	
	/**
	 * 检测竞技点恢复
	 * @param  AthleticsInfo 用户
	 */
	@Transaction
	void checkAthleticsInfoPoint(AthleticsInfo info);
	
	/**
	 * 获得称号奖励
	 * @return 所有称号奖励
	 */
	List<AthleticsInfoAward> getAthleticsNameAward();

}
