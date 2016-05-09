package com.ks.logic.service;

import com.ks.access.Transaction;
import com.ks.model.shop.ProductRecord;

/**
 * 商品记录 Service
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2015年8月18日
 */
public interface ProductRecordService {
	
	/**
	 * 添加商品记录
	 * @param entity
	 */
	@Transaction
	void add(ProductRecord entity);
	
	/**
	 * 修改商品记录
	 * @param entity
	 */
	@Transaction
	void update(ProductRecord entity);
	
	/**
	 * 删除商品记录
	 * @param id
	 */
	@Transaction
	void delete(int id);
}