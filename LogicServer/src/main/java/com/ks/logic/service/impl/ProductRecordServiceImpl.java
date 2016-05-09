package com.ks.logic.service.impl;

import com.ks.logic.service.BaseService;
import com.ks.logic.service.ProductRecordService;
import com.ks.model.shop.ProductRecord;

public class ProductRecordServiceImpl extends BaseService implements ProductRecordService {

	@Override
    public void add(ProductRecord entity) {
	    productRecordDAO.add(entity);
    }

	@Override
    public void update(ProductRecord entity) {
		productRecordDAO.update(entity);
    }

	@Override
    public void delete(int id) {
		productRecordDAO.delete(id);
    }
}
