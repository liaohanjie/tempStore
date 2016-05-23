package com.ks.account.service.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ks.account.service.AccountService;
import com.ks.account.service.BaseService;
import com.ks.model.account.Account;

public class AccountServiceImpl extends BaseService implements AccountService {

	@Override
	public Account queryById(Integer id) {
		return accountDAO.queryById(id);
	}

	@Override
	public Account queryByUserId(Integer userId) {
		return accountDAO.queryByUserId(userId);
	}

	@Override
	public Account queryByPartnerIdUserName(Integer partnerId, String userName) {
		return accountDAO.queryByPartnerIdUserName(partnerId, userName);
	}

	@Override
	public void add(Account entity) {
		accountDAO.add(entity);
	}

	@Override
	public void update(Account entity) {
		accountDAO.update(entity);
	}

	@Override
	public void delete(Integer userId) {
		accountDAO.delete(userId);
	}

	@Override
	public List<Map<String, Object>> statisticsUserPartner() {
		List<Map<String, Object>> list = null;
		try {
			list = accountDAO.statisticsUserPartner();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

}
