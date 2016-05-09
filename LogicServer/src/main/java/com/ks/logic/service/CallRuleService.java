package com.ks.logic.service;

import java.util.List;

import com.ks.model.soul.CallRule;


/**
 * 召唤权重 Service
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2015年9月10日
 */
public interface CallRuleService {
//	
//	/**
//	 * 添加
//	 * @param entity
//	 */
//	@Transaction
//	void add(CallRule entity);
//	
//	/**
//	 * 修改
//	 * @param entity
//	 */
//	@Transaction
//	void update(CallRule entity);
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
	List<CallRule> queryAll();
	
}