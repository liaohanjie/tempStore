package com.ks.logic.service.impl;

import com.ks.logic.service.BaseService;
import com.ks.logic.service.StatService;
import com.ks.model.game.Stat;

/**
 * 全局状态
 * 
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2016年1月11日
 */
public class StatServiceImpl extends BaseService implements StatService {

	@Override
    public Stat findById(int id) {
	    return statDAO.findById(id);
    }
}
