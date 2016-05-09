package com.ks.logic.service;

import java.util.List;

import com.ks.model.buding.Buding;
import com.ks.model.buding.BudingDrop;
import com.ks.model.buding.BudingRule;

public interface BudingService {
	/**
	 * 获得所有建筑
	 * @return 所有建筑
	 */
	public List<Buding> queryAllBuding();
	/**
	 * 获得所有建筑规则
	 * @return 建筑规则
	 */
	public List<BudingRule> queryAllBudingRule();
	/**
	 * 获得所有建筑掉落
	 * @return 建筑掉落
	 */
	public List<BudingDrop> queryAllBudingDrop();
}
