package com.ks.account.action;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ks.account.service.AccountService;
import com.ks.account.service.PayService;
import com.ks.account.service.ServerInfoService;
import com.ks.account.service.ServiceFactory;
import com.ks.action.account.AccountAction;
import com.ks.logger.LoggerFactory;
import com.ks.model.account.Account;
import com.ks.model.account.ServerInfo;

public class AccountActionImpl implements AccountAction {
	
	private Logger logger = LoggerFactory.get(getClass());
	
	private static final AccountService accountService = ServiceFactory.getService(AccountService.class);

	@Override
    public Account queryById(Integer id) {
	    return accountService.queryById(id);
    }

	@Override
    public Account queryByUserId(Integer userId) {
	    return accountService.queryByUserId(userId);
    }

	@Override
    public Account queryByPartnerIdUserName(Integer partnerId, String userName) {
	    return accountService.queryByPartnerIdUserName(partnerId, userName);
    }

	@Override
    public void add(Account entity) {
		accountService.add(entity);
    }

	@Override
    public void update(Account entity) {
		accountService.update(entity);
    }

	@Override
    public void delete(Account entity) {
		accountService.delete(entity.getUserId());
    }

	@Override
    public Account login(Account form) {
	    Account account = accountService.queryByPartnerIdUserName(form.getPartnerId(), form.getUserName());
	    if(account == null) {
	    	form.setLoginCount(1);
	    	form.setCreateTime(new Date());
	    	form.setStatus(1);
	    	accountService.add(form);
	    } else {
	    	form.setId(account.getId());
	    	form.setLoginCount(account.getLoginCount() + 1);;
	    	accountService.update(form);
	    	
	    	try {
	    		ServerInfoService serverInfoService = ServiceFactory.getService(ServerInfoService.class);
	    		ServerInfo serverInfo = serverInfoService.getServerList().get(0);
		    	
		    	PayService payService = ServiceFactory.getService(PayService.class);
		    	payService.orderReturn(serverInfo.getServerId(), form.getPartnerId(), form.getUserName());
	    	} catch(Exception e) {
	    		logger.error(e);
	    	}
	    }
	    return form;
    }

	@Override
    public List<Map<String, Object>> statisticsUserPartner() {
	    return accountService.statisticsUserPartner();
    }
}
