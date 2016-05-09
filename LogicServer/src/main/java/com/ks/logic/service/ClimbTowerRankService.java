package com.ks.logic.service;

import java.util.List;

import com.ks.access.Transaction;
import com.ks.model.climb.ClimbTowerRank;
import com.ks.protocol.vo.rank.RankNoticeVO;


/**
 * 爬塔试炼用户爬塔排行 Service
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2015年11月13日
 */
public interface ClimbTowerRankService {

	@Transaction
	void add(ClimbTowerRank entity);
	
	@Transaction
	void update(ClimbTowerRank entity);
	
	/**
	 * 查询第几层用户爬塔信息
	 * @param userId
	 * @param towerFloor
	 * @return
	 */
//	ClimbTowerRank findByUserIdTowerFloor(int userId, int towerFloor);
	
	/**
	 * 查询排行数据
	 * @param size
	 * @return
	 */
	List<ClimbTowerRank> queryAll(int size);

	/**
	 * 获取爬塔排名信息
	 * @param userId
	 * @return
	 */
	RankNoticeVO getClimbTowerRank(int userId);
	
}