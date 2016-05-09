package com.ks.logic.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.ks.logic.service.BaseService;
import com.ks.logic.service.ProductItemService;
import com.ks.model.shop.ProductItem;
import com.ks.protocol.MessageFactory;
import com.ks.protocol.vo.shop.ProductItemVO;

public class ProductItemServiceImpl extends BaseService implements ProductItemService {

	@Override
    public void addProductItem(ProductItem entity) {
	    // TODO Auto-generated method stub
	    
    }

	@Override
    public void updateProductItem(ProductItem entity) {
	    // TODO Auto-generated method stub
	    
    }

	@Override
    public void deleteProductItem(int id) {
	    // TODO Auto-generated method stub
	    
    }

	@Override
    public List<ProductItemVO> queryProductItemByProductId(int productId) {
	    // TODO Auto-generated method stub
	    return null;
    }

	@Override
    public ProductItem findProductItemByTypeId(int id) {
	    // TODO Auto-generated method stub
	    return null;
    }
	
	@Override
    public ProductItemVO createProductItemVO(ProductItem entity) {
		ProductItemVO vo = MessageFactory.getMessage(ProductItemVO.class);
		vo.initProductItemVO(entity.getId(), entity.getProductId(), entity.getGoodsId(), entity.getGoodsType(), entity.getGoodsNum(), entity.getGoodsLevel(), entity.getRate());
	    return vo;
    }

	@Override
    public List<ProductItemVO> createProductItemVOList(List<ProductItem> list) {
		List<ProductItemVO> listVo = new ArrayList<ProductItemVO>();
		for(ProductItem entity : list) {
			listVo.add(createProductItemVO(entity));
	    }
	    return listVo;
    }
	
}
