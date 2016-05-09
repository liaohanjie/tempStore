package com.ks.logic.service.impl;

import java.util.List;

import com.ks.logic.service.BaseService;
import com.ks.logic.service.CoinHandService;
import com.ks.model.coin.CoinHand;

/**
 * 图腾规则
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2015年9月9日
 */
public class CoinHandServiceImpl extends BaseService implements CoinHandService {
//	
//	@Override
//    public void add(CoinHand entity) {
//		coinHandDAO.add(entity);
//    }
//
//	@Override
//    public void update(CoinHand entity) {
//		coinHandDAO.update(entity);
//    }
//
//	@Override
//    public void delete(int id) {
//		coinHandDAO.delete(id);
//    }

	@Override
    public List<CoinHand> queryAll() {
	    return coinHandDAO.queryAll();
    }
}
