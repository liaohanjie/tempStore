package com.ks.account.service;

import java.util.List;

import com.ks.access.Transaction;
import com.ks.model.account.PayConfig;

/**
 * 合作商渠道配置  Service
 * 
 * @author zhoujf
 * @date 2015年6月24日
 */
public interface PayConfigService {

	/**
	 * 根据ID查找
	 * @param id
	 * @return
	 */
	PayConfig queryById(Integer id);
	
	/**
	 * 查询所有
	 * @return
	 */
	List<PayConfig> queryAll();
	
	/**
	 * 添加
	 * @param entity
	 */
	@Transaction
	void add(PayConfig entity);
	
	/**
	 * 修改
	 * @param entity
	 */
	@Transaction
	void update(PayConfig entity);
	
	/**
	 * 删除
	 * @param id
	 */
	@Transaction
	void delete(Integer id);
}
