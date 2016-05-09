package com.ks.logic.service;

import java.util.List;

import com.ks.access.Transaction;
import com.ks.model.climb.ClimbTowerAwardRecord;


/**
 * 炼爬塔集星奖励领取记录 Service
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2015年11月13日
 */
public interface ClimbTowerAwardRecordService {

	@Transaction
	void add(ClimbTowerAwardRecord entity);
	
	@Transaction
	void update(ClimbTowerAwardRecord entity);
	
	/**
	 * 查询用户的领取状态
	 * @param userId
	 * @return
	 */
	List<ClimbTowerAwardRecord> queryByUserId(int userId);
	
	/**
	 * 查询指定星集数量的领取记录
	 * @param userId
	 * @param starRegion
	 * @param startNum
	 * @return
	 */
	ClimbTowerAwardRecord findByUserId(int userId, int starRegion, int startNum);

	/**
	 * 查询用户每个区域的星星领取记录
	 * @param userId
	 * @return
	 */
	List<ClimbTowerAwardRecord> queryClimbTowerRegionStarRecord(int userId);
}