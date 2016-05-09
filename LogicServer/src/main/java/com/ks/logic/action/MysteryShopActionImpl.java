package com.ks.logic.action;

import java.util.List;

import com.ks.action.logic.MysteryShopAction;
import com.ks.logic.service.MysteryShopItemService;
import com.ks.logic.service.ServiceFactory;
import com.ks.protocol.vo.mission.UserAwardVO;
import com.ks.protocol.vo.shop.MysteryShopItemVO;

/**
 * 神秘商店
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2016年1月15日
 */
public  class MysteryShopActionImpl implements MysteryShopAction {
	
	private static MysteryShopItemService mysteryShopItemService = ServiceFactory.getService(MysteryShopItemService.class);

	@Override
    public List<MysteryShopItemVO> queryAll(int userId) {
	    return mysteryShopItemService.queryByUserId(userId);
    }

	@Override
    public List<MysteryShopItemVO> fresh(int userId, int typeId) {
	    return mysteryShopItemService.fresh(userId, typeId);
    }

	@Override
    public UserAwardVO mysteryShopBuy(int userId, int id) {
	    return mysteryShopItemService.buy(userId, id);
    }

	@Override
    public List<Integer> queryCurrentAllRecord(int userId) {
	    return mysteryShopItemService.queryCurrentAllRecord(userId);
    }
}
