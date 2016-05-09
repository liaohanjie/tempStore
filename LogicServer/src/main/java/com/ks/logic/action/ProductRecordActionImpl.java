package com.ks.logic.action;

import java.util.Map;

import com.ks.action.logic.ProductRecordAction;
import com.ks.logic.service.ProductRecordService;
import com.ks.logic.service.ServiceFactory;
import com.ks.model.PageResult;
import com.ks.model.shop.ProductRecord;

public  class ProductRecordActionImpl implements ProductRecordAction {
	
	private static ProductRecordService productRecordService = ServiceFactory.getService(ProductRecordService.class);

	@Override
    public PageResult<ProductRecord> findByPage(Map<String, Object> params, Integer offset, Integer rows) throws Exception {
		//productRecordService
	    return null;
    }
	
}
