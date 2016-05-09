package com.ks.action.account;

import java.util.List;

import com.ks.model.activity.GiftTemplate;
import com.ks.model.goods.GoodsTemplate;

/***
 * 礼包模版
 * 
 * @author lipp 2015年7月7日
 */
public interface GiftTemplateAction {

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
	void updateGiftTemplate(GiftTemplate gift);

	/**
	 * 增加礼包模版
	 * 
	 * @param admin
	 */
	void addGiftTemplate(GiftTemplate gift);

	/**
	 * 删除礼包模版
	 * 
	 * @param id
	 */
	void deleteGiftTemplate(int id);

	/**
	 * 查询所有物品
	 * @return
	 */
	List<GoodsTemplate> queryGoodsTemplate();
}
