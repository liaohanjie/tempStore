package com.ks.account.action;

import java.util.List;

import com.ks.account.service.PayConfigService;
import com.ks.account.service.ServiceFactory;
import com.ks.action.account.PayConfigAction;
import com.ks.model.account.PayConfig;

/**
 * 渠道支付配置 Service
 * 
 * @author zhoujf
 * @date 2015年6月24日
 */
public class PayConfigActionImpl implements PayConfigAction {
	
	private static final PayConfigService payConfigService= ServiceFactory.getService(PayConfigService.class);

	@Override
    public PayConfig queryById(Integer id) {
	    return payConfigService.queryById(id);
    }

	@Override
    public List<PayConfig> queryAll() {
	    return payConfigService.queryAll();
    }

	@Override
    public void add(PayConfig entity) {
		payConfigService.add(entity);
    }

	@Override
    public void update(PayConfig entity) {
		payConfigService.update(entity);
    }

	@Override
    public void delete(PayConfig entity) {
		payConfigService.delete(entity.getId());
    }
}
