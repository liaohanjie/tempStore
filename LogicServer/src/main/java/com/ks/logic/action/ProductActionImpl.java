package com.ks.logic.action;

import java.util.Map;

import com.ks.action.logic.ProductAction;
import com.ks.logic.service.ProductService;
import com.ks.logic.service.ServiceFactory;
import com.ks.model.PageResult;
import com.ks.model.shop.Product;
import com.ks.protocol.vo.mission.UserAwardVO;

public  class ProductActionImpl implements ProductAction{
	
	private static ProductService productService = ServiceFactory.getService(ProductService.class);

	@Override
    public UserAwardVO bugProduct(int userId, int typeId) {
	    return productService.buyProduct(userId, typeId);
    }

	@Override
    public PageResult<Product> findByPage(Map<String, Object> params, Integer offset, Integer rows) throws Exception {
	    // TODO Auto-generated method stub
	    return null;
    }
	
}
