package com.ks.account.service.impl;

import java.util.List;

import com.ks.account.service.BaseService;
import com.ks.account.service.PayConfigService;
import com.ks.model.account.PayConfig;

/**
 * 渠道支付配置
 * 
 * @author zhoujf
 * @date 2015年6月24日
 */
public class PayConfigServiceImpl extends BaseService implements PayConfigService {

	@Override
    public PayConfig queryById(Integer id) {
	    return payConfigDAO.queryById(id);
    }

	@Override
    public void add(PayConfig entity) {
		payConfigDAO.add(entity);
    }

	@Override
    public void update(PayConfig entity) {
		payConfigDAO.update(entity);
    }

	@Override
    public void delete(Integer id) {
		payConfigDAO.delete(id);
    }

	@Override
    public List<PayConfig> queryAll() {
	    return payConfigDAO.queryAll();
    }
}
