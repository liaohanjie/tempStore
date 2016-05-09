package com.ks.action.logic;

import java.util.Map;

import com.ks.model.PageResult;
import com.ks.model.shop.Product;
import com.ks.protocol.vo.mission.UserAwardVO;


public interface ProductAction {
	
	/**
	 * 购买商品
	 * @param userId
	 * @param typeId
	 * @return
	 */
	UserAwardVO bugProduct(int userId, int typeId); 
	
	/**
	 * 分页条件查询
	 * @param params
	 * @param offset
	 * @param rows
	 * @return
	 * @throws Exception
	 */
	PageResult<Product> findByPage(Map<String, Object> params, Integer offset, Integer rows) throws Exception;
}
