package com.ks.logic.service.impl;

import java.util.List;

import com.ks.logic.service.BaseService;
import com.ks.logic.service.CallRuleService;
import com.ks.model.soul.CallRule;

/**
 * 图腾规则
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2015年9月9日
 */
public class CallRuleServiceImpl extends BaseService implements CallRuleService {
	
//	@Override
//    public void add(CallRule entity) {
//		callRuleDAO.add(entity);
//    }
//
//	@Override
//    public void update(CallRule entity) {
//		callRuleDAO.update(entity);
//    }
//
//	@Override
//    public void delete(int id) {
//		callRuleDAO.delete(id);
//    }

	@Override
    public List<CallRule> queryAll() {
	    return callRuleDAO.queryAll();
    }
}
