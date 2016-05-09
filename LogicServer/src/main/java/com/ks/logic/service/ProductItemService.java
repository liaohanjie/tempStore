package com.ks.logic.service;

import java.util.List;

import com.ks.access.Transaction;
import com.ks.model.shop.ProductItem;
import com.ks.protocol.vo.shop.ProductItemVO;


/**
 * 商品物品 Service
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2015年7月16日
 */
public interface ProductItemService {
	
	/**
	 * 添加商品物品
	 * @param entity
	 */
	@Transaction
	void addProductItem(ProductItem entity);
	
	/**
	 * 修改商品物品
	 * @param entity
	 */
	@Transaction
	void updateProductItem(ProductItem entity);
	
	/**
	 * 删除商品物品
	 * @param id
	 */
	@Transaction
	void deleteProductItem(int id);
	
	/**
	 * 按照分类查找所有商品物品
	 * @param productId
	 * @return
	 */
	List<ProductItemVO> queryProductItemByProductId(int productId);
	
	/**
	 * 按照类型查找商品物品对象
	 * @param typeId
	 * @return
	 */
	ProductItem findProductItemByTypeId(int id);
	
	/**
	 * 创建 ProductItemVO
	 * @param entity
	 * @return
	 */
	ProductItemVO createProductItemVO(ProductItem entity);
	
	/**
	 * 创建 ProductItemVO List
	 * @param entity
	 * @return
	 */
	List<ProductItemVO> createProductItemVOList(List<ProductItem> list);
	
}