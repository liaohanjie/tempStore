package com.ks.logic.service.impl;

import java.util.List;

import com.ks.logic.service.BaseService;
import com.ks.logic.service.AwardService;
import com.ks.model.Award;

/**
 * 奖励配置 Service
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2015年11月17日
 */
public class AwardServiceImpl extends BaseService implements AwardService {

	@Override
    public List<Award> queryAll() {
	    return climbTowerAwardDAO.queryAll();
    }

}
