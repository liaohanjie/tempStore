package com.ks.logic.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.ks.logic.service.BaseService;
import com.ks.logic.service.ClimbTowerUserService;
import com.ks.model.climb.ClimbTowerUser;

/**
 * 爬塔试炼配置
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2015年11月17日
 */
public class ClimbTowerUserServiceImpl extends BaseService implements ClimbTowerUserService {

	@Override
    public void add(ClimbTowerUser entity) {
		climbTowerUserDAO.add(entity);
    }

	@Override
    public void update(ClimbTowerUser entity) {
		climbTowerUserDAO.update(entity);
    }

	@Override
    public ClimbTowerUser findByUserIdTowerFloor(int userId, int towerFloor) {
		return climbTowerUserDAO.findClimbTowerUser(userId, towerFloor);
    }

	@Override
    public List<ClimbTowerUser> queryByUserId(int userId) {
	    return climbTowerUserDAO.queryClimbTowerUser(userId);
    }
	
	@Override
	public void refreshClimbTower(int userId) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE,0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		
		// 下次更新时间
		Date nextUpdateTime = calendar.getTime();
		climbTowerUserDAO.refreshClimbTower(userId, nextUpdateTime);
	}
}
