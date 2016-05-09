package com.ks.logic.service.impl;

import java.util.List;

import com.ks.logic.service.BaseService;
import com.ks.logic.service.TotemRuleService;
import com.ks.model.totem.TotemRule;

/**
 * 图腾规则
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2015年9月9日
 */
public class TotemRuleServiceImpl extends BaseService implements TotemRuleService {

//	@Override
//    public void add(TotemRule entity) {
//	    totemRuleDAO.add(entity);
//    }
//
//	@Override
//    public void update(TotemRule entity) {
//		totemRuleDAO.update(entity);
//    }
//
//	@Override
//    public void delete(int id) {
//		totemRuleDAO.delete(id);
//    }

	@Override
    public List<TotemRule> queryAll() {
	    return totemRuleDAO.queryAll();
    }
}
