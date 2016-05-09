package com.ks.logic.service.impl;

import java.util.List;

import com.ks.logic.service.BaseService;
import com.ks.logic.service.ClimbTowerStarService;
import com.ks.model.climb.ClimbTowerStar;

/**
 * 爬塔试炼集星奖励配置
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2015年11月17日
 */
public class ClimbTowerStarServiceImpl extends BaseService implements ClimbTowerStarService {

	@Override
    public List<ClimbTowerStar> queryAll() {
	    return climbTowerStarDAO.queryAll();
    }

}
