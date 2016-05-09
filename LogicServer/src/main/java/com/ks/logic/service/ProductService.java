package com.ks.logic.service;

import java.util.List;

import com.ks.access.Transaction;
import com.ks.model.shop.Product;
import com.ks.protocol.vo.mission.UserAwardVO;
import com.ks.protocol.vo.shop.ProductBuyCountVO;
import com.ks.protocol.vo.shop.ProductVO;


/**
 * 商品 Service
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2015年7月16日
 */
public interface ProductService {
	
	/**
	 * 添加商品
	 * @param entity
	 */
	@Transaction
	void addProduct(Product entity);
	
	/**
	 * 修改商品
	 * @param entity
	 */
	@Transaction
	void updateProduct(Product entity);
	
	/**
	 * 删除商品
	 * @param id
	 */
	@Transaction
	void deleteProduct(int id);
	
	/**
	 * 按照分类查找所有商品
	 * @param classId
	 * @return
	 */
	List<ProductVO> queryProductByClassId(int classId);
	
	/**
	 * 按照类型查找商品对象
	 * @param typeId
	 * @return
	 */
	ProductVO findProductByTypeId(int typeId);
	
	/**
	 * 玩家购买商品
	 * @param userId
	 * @param typeId
	 * @return
	 */
	@Transaction
	UserAwardVO buyProduct(int userId, int typeId);
	
	/**
	 * 创建 ProductVO
	 * @param entity
	 * @return
	 */
	ProductVO createProductVO(Product entity);
	
	/**
	 * 创建 ProductVO List
	 * @param entity
	 * @return
	 */
	List<ProductVO> createProductVOList(List<Product> list);
	
	/**
	 * 1_2,3_4,... 解析成 List<ProductBuyCountVO>
	 * @param string
	 * @return
	 */
	List<ProductBuyCountVO> createProductBuyCountVOList(String string);
	
	/**
	 * 给指定 productTypeId 数量 +1，并把 List<ProductBuyCountVO> 转换成 1_2,3_4,... 格式字符串
	 * @param list
	 * @return
	 */
	String createProductBuyCountVOString(List<ProductBuyCountVO> list, ProductVO productVO);
}