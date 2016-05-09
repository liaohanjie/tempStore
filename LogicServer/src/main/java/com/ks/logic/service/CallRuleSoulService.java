package com.ks.logic.service;

import java.util.List;

import com.ks.access.Transaction;
import com.ks.model.soul.CallRuleSoul;


/**
 * 召唤战魂权重 Service
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2015年9月10日
 */
public interface CallRuleSoulService {
	
//	/**
//	 * 添加
//	 * @param entity
//	 */
//	@Transaction
//	void add(CallRuleSoul entity);
	
//	/**
//	 * 修改
//	 * @param entity
//	 */
//	@Transaction
//	void update(CallRuleSoul entity);
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
	List<CallRuleSoul> queryAll();

}