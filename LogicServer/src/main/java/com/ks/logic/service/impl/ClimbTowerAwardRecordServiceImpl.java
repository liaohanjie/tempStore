package com.ks.logic.service.impl;

import java.util.List;

import com.ks.logic.service.BaseService;
import com.ks.logic.service.ClimbTowerAwardRecordService;
import com.ks.model.climb.ClimbTowerAwardRecord;

/**
 * 爬塔试炼集星奖励配置
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2015年11月17日
 */
public class ClimbTowerAwardRecordServiceImpl extends BaseService implements ClimbTowerAwardRecordService {

	@Override
    public void add(ClimbTowerAwardRecord entity) {
		climbTowerAwardRecordDAO.add(entity);
    }

	@Override
    public void update(ClimbTowerAwardRecord entity) {
		climbTowerAwardRecordDAO.update(entity);
    }

	@Override
    public List<ClimbTowerAwardRecord> queryByUserId(int userId) {
	    return climbTowerAwardRecordDAO.queryClimbTowerAwardRecord(userId);
    }

	@Override
    public ClimbTowerAwardRecord findByUserId(int userId, int starRegion, int startNum) {
	    return climbTowerAwardRecordDAO.queryClimbTowerAwardRecord(userId, starRegion, startNum);
    }

	@Override
	public List<ClimbTowerAwardRecord> queryClimbTowerRegionStarRecord(int userId) {
		return climbTowerAwardRecordDAO.queryClimbTowerRegionStarRecord(userId);
	}
}
