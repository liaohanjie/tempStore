package com.ks.logic.service.impl;

import java.util.List;

import com.ks.logic.service.BaseService;
import com.ks.logic.service.CallRuleSoulService;
import com.ks.model.soul.CallRuleSoul;

/**
 * 图腾规则
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2015年9月9日
 */
public class CallRuleSoulServiceImpl extends BaseService implements CallRuleSoulService {
	
//	@Override
//    public void add(CallRuleSoul entity) {
//		callRuleSoulDAO.add(entity);
//    }
//
//	@Override
//    public void update(CallRuleSoul entity) {
//		callRuleSoulDAO.update(entity);
//    }
//
//	@Override
//    public void delete(int id) {
//		callRuleSoulDAO.delete(id);
//    }

	@Override
    public List<CallRuleSoul> queryAll() {
	    return callRuleSoulDAO.queryAll();
    }
	
}
