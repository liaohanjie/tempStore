package com.ks.account.service.impl;

import java.util.List;

import com.ks.account.service.BaseService;
import com.ks.account.service.GiftTemplateService;
import com.ks.model.activity.GiftTemplate;
import com.ks.model.goods.GoodsTemplate;

public class GiftTemplateServiceImpl extends BaseService implements GiftTemplateService {

	@Override
	public void updateGiftTemplate(GiftTemplate gift) {
		templateDAO.updateGiftTemplate(gift);
	}

	@Override
	public void addGiftTemplate(GiftTemplate gift) {
		templateDAO.addGiftTemplate(gift);
	}

	@Override
	public void deleteGiftTemplate(int id) {
		templateDAO.deleteGiftTemplate(id);
	}

	@Override
	public List<GiftTemplate> queryGiftTemplate() {
		return templateDAO.queryGiftTemplate();
	}

	@Override
	public List<GoodsTemplate> queryGoodsTemplate() {
		return templateDAO.queryGoodsTemplate();
	}

}
