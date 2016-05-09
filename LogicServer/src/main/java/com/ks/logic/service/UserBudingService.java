package com.ks.logic.service;

import java.util.List;

import com.ks.access.Transaction;
import com.ks.model.buding.UserBuding;
import com.ks.protocol.vo.buding.CollectResultVO;
import com.ks.protocol.vo.buding.LevelUpBudingResultVO;
import com.ks.protocol.vo.buding.UserBudingVO;

/**
 * 用户建筑
 * @author ks
 */
public interface UserBudingService {
	/**
	 * 获取用户建筑
	 * @param userId 用户编号
	 * @return 建筑编号
	 */
	@Transaction
	List<UserBudingVO> gainUserBudings(int userId);
	/**
	 * 升级建筑
	 * @param userId 用户编号
	 * @param budingId 建筑编号
	 * @param gold 金钱
	 * @return 升级后的建筑
	 */
	@Transaction
	LevelUpBudingResultVO levelUpBuding(int userId,int budingId,int gold);
	
	/**
	 * 获取建筑
	 * @param userId 用户编号
	 * @param budingId 建筑编号
	 * @return 建筑
	 */
	UserBuding getExistUserBuding(int userId,int budingId);
	
	/**
	 * 收集建筑
	 * @param userId 用户编号
	 * @param budingId 建筑编号
	 * @param count 次数
	 * @return 收集结果
	 */
	@Deprecated
	@Transaction
	CollectResultVO collectBuding(int userId,int budingId,int count);
	
	/**
	 * 收集单个建筑所有收获
	 * @param userId
	 * @param budingId
	 * @return
	 */
	@Transaction
	CollectResultVO collectBuding(int userId, int budingId);
}
