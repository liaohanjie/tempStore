package com.ks.action.account;

import java.util.List;

import com.ks.model.account.PayConfig;



/**
 * 渠道支付配置 Action
 * 
 * @author zhoujf
 * @date 2015年6月24日
 */
public interface PayConfigAction {
	
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
	void add(PayConfig entity);
	
	/**
	 * 修改
	 * @param entity
	 */
	void update(PayConfig entity);
	
	/**
	 * 删除
	 * @param entity
	 */
	void delete(PayConfig entity);
}
