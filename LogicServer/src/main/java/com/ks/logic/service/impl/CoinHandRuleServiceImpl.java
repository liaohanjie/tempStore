package com.ks.logic.service.impl;

import java.util.List;

import com.ks.logic.service.BaseService;
import com.ks.logic.service.CoinHandRuleService;
import com.ks.model.coin.CoinHandRule;

/**
 * 图腾规则
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2015年9月9日
 */
public class CoinHandRuleServiceImpl extends BaseService implements CoinHandRuleService {
	
//	@Override
//    public void add(CoinHandRule entity) {
//		coinHandRuleDAO.add(entity);
//    }
//
//	@Override
//    public void update(CoinHandRule entity) {
//		coinHandRuleDAO.update(entity);
//    }
//
//	@Override
//    public void delete(int id) {
//		coinHandRuleDAO.delete(id);
//    }

	@Override
    public List<CoinHandRule> queryAll() {
	    return coinHandRuleDAO.queryAll();
    }
}
