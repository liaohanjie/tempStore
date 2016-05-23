package com.ks.account.action;

import java.util.List;

import com.ks.account.service.GiftTemplateService;
import com.ks.account.service.ServiceFactory;
import com.ks.action.account.GiftTemplateAction;
import com.ks.model.activity.GiftTemplate;
import com.ks.model.goods.GoodsTemplate;

public class GiftTemplateActionImpl implements GiftTemplateAction {
	private static final GiftTemplateService templateService = ServiceFactory.getService(GiftTemplateService.class);

	@Override
	public void updateGiftTemplate(GiftTemplate gift) {
		templateService.updateGiftTemplate(gift);
	}

	@Override
	public void addGiftTemplate(GiftTemplate gift) {
		templateService.addGiftTemplate(gift);
	}

	@Override
	public void deleteGiftTemplate(int id) {
		templateService.deleteGiftTemplate(id);
	}

	@Override
	public List<GiftTemplate> queryGiftTemplate() {
		return templateService.queryGiftTemplate();
	}

	@Override
    public List<GoodsTemplate> queryGoodsTemplate() {
	    return templateService.queryGoodsTemplate();
    }

}
