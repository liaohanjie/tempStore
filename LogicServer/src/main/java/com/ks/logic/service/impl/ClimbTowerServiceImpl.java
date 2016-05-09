package com.ks.logic.service.impl;

import java.util.List;

import com.ks.logic.service.BaseService;
import com.ks.logic.service.ClimbTowerService;
import com.ks.model.climb.ClimbTower;

/**
 * 爬塔试炼配置
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2015年11月17日
 */
public class ClimbTowerServiceImpl extends BaseService implements ClimbTowerService {

	@Override
    public List<ClimbTower> queryAll() {
	    return climbTowerDAO.queryAll();
    }
}
