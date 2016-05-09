package com.ks.action.logic;

import com.ks.protocol.vo.pvp.AthleticsInfoModelVO;
import com.ks.protocol.vo.pvp.AthleticsInfoVO;

public interface AthleticsInfoAction {
	/**
	 * 排行榜信息
	 * @return
	 */
	public AthleticsInfoModelVO getAthleticsInfo(int userId);
	/**
	 * 匹配到的对手信息
	 * @param userId
	 * @return
	 */
	public AthleticsInfoVO getMatchUser(int userId);
	
	/**
	 * 进入竞技场
	 * @return
	 */
	public AthleticsInfoVO enterArnea(int userId);
	
	/**
	 * 开始PK
	 * @return
	 */
	public AthleticsInfoVO startArneaPK(int attackerId, int defenderId);
	/**
	 * 花钱恢复竞技点数
	 * @return int
	 */
	public int regainAthleticsPoint(int userId);

}
