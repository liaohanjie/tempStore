package com.ks.logic.service;

import java.util.List;

import com.ks.model.totem.TotemSoul;


/**
 * 图腾规则战魂产出 Service
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2015年9月9日
 */
public interface TotemSoulService {
	
//	/**
//	 * 添加
//	 * @param entity
//	 */
//	@Transaction
//	void add(TotemSoul entity);
//	
//	/**
//	 * 修改
//	 * @param entity
//	 */
//	@Transaction
//	void update(TotemSoul entity);
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
	List<TotemSoul> queryAll();
	
}