package com.ks.action.logic;

import java.util.Map;

import com.ks.model.PageResult;
import com.ks.model.shop.ProductRecord;

public interface ProductRecordAction {
	
	/**
	 * 分页条件查询
	 * @param params
	 * @param offset
	 * @param rows
	 * @return
	 * @throws Exception
	 */
	PageResult<ProductRecord> findByPage(Map<String, Object> params, Integer offset, Integer rows) throws Exception;
}
