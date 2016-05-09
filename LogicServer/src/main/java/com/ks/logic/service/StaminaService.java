package com.ks.logic.service;

import java.util.List;

import com.ks.access.Transaction;
import com.ks.model.stamina.Stamina;


/**
 * 购买体力配置  Service
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2015年9月18日
 */
public interface StaminaService {
//	
//	/**
//	 * 添加
//	 * @param entity
//	 */
//	@Transaction
//	void add(Stamina entity);
//	
//	/**
//	 * 修改
//	 * @param entity
//	 */
//	@Transaction
//	void update(Stamina entity);
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
	List<Stamina> queryAll();
	
}