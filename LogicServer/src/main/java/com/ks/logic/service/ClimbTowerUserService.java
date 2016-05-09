package com.ks.logic.service;

import java.util.List;

import com.ks.access.Transaction;
import com.ks.model.climb.ClimbTowerUser;


/**
 * 爬塔试炼用户爬塔层数记录 Service
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2015年11月13日
 */
public interface ClimbTowerUserService {

	@Transaction
	void add(ClimbTowerUser entity);
	
	@Transaction
	void update(ClimbTowerUser entity);
	
	/**
	 * 查询第几层用户爬塔信息
	 * @param userId
	 * @param towerFloor
	 * @return
	 */
	ClimbTowerUser findByUserIdTowerFloor(int userId, int towerFloor);
	
	/**
	 * 
	 * @param userId
	 * @return
	 */
	List<ClimbTowerUser> queryByUserId(int userId);

	/**
	 * 刷新爬塔用户数据
	 * @param userId
	 */
	@Transaction
	void refreshClimbTower(int userId);
	
}