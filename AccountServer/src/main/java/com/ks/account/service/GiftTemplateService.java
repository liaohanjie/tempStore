package com.ks.account.service;

import java.util.List;

import com.ks.access.Transaction;
import com.ks.model.activity.GiftTemplate;
import com.ks.model.goods.GoodsTemplate;

public interface GiftTemplateService {
	/**
	 * 查询礼包模版
	 * 
	 * @return
	 */
	List<GiftTemplate> queryGiftTemplate();

	/**
	 * 修改礼包模版
	 * 
	 * @param admin
	 */
	@Transaction
	void updateGiftTemplate(GiftTemplate gift);

	/**
	 * 增加礼包模版
	 * 
	 * @param admin
	 */
	@Transaction
	void addGiftTemplate(GiftTemplate gift);

	/**
	 * 删除礼包模版
	 * 
	 * @param id
	 */
	@Transaction
	void deleteGiftTemplate(int id);

	/**
	 * 查询所有物品
	 * 
	 * @return
	 */
	List<GoodsTemplate> queryGoodsTemplate();
}
