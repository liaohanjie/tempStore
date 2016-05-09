package com.ks.logic.service;

import java.util.List;

import com.ks.access.Transaction;
import com.ks.model.coin.CoinHandRule;


/**
 * 金币手  Service
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2015年9月11日
 */
public interface CoinHandRuleService {
//	
//	/**
//	 * 添加
//	 * @param entity
//	 */
//	@Transaction
//	void add(CoinHandRule entity);
//	
//	/**
//	 * 修改
//	 * @param entity
//	 */
//	@Transaction
//	void update(CoinHandRule entity);
//	
//	/**
//	 * 删除
//	 * @param id
//	 */
//	@Transaction
//	void delete(int id);
	
	/**
	 * 查找所有
	 * @param classId
	 * @return
	 */
	List<CoinHandRule> queryAll();
	
}