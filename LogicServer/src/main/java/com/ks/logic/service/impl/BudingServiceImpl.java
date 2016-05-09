package com.ks.logic.service.impl;

import java.util.List;

import com.ks.logic.service.BaseService;
import com.ks.logic.service.BudingService;
import com.ks.model.buding.Buding;
import com.ks.model.buding.BudingDrop;
import com.ks.model.buding.BudingRule;

public class BudingServiceImpl extends BaseService implements BudingService{

	@Override
	public List<Buding> queryAllBuding() {
		return budingDAO.queryAllBuding();
	}

	@Override
	public List<BudingRule> queryAllBudingRule() {
		return budingDAO.queryAllBudingRule();
	}

	@Override
	public List<BudingDrop> queryAllBudingDrop() {
		return budingDAO.queryAllBudingDrop();
	}

}
