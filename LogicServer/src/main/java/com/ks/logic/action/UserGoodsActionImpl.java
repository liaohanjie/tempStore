package com.ks.logic.action;

import java.util.List;

import com.ks.action.logic.UserGoodsAction;
import com.ks.logic.service.ServiceFactory;
import com.ks.logic.service.UserGoodsService;
import com.ks.protocol.vo.goods.EqRefiningVO;
import com.ks.protocol.vo.goods.EqRepairRetVO;
import com.ks.protocol.vo.goods.FightPropVO;
import com.ks.protocol.vo.goods.GainAwardVO;
import com.ks.protocol.vo.goods.SellGoodsResultVO;
import com.ks.protocol.vo.goods.SellGoodsVO;
import com.ks.protocol.vo.goods.UserBakPropVO;
import com.ks.protocol.vo.goods.UserGoodsVO;

public class UserGoodsActionImpl implements UserGoodsAction {
	
	private static final UserGoodsService userGoodsService = ServiceFactory.getService(UserGoodsService.class);
	
	@Override
	public void useEquipment(int userId, long userSoulId, int gridId) {
		userGoodsService.useEquipment(userId, userSoulId, gridId);
	}

	@Override
	public void unUseEquipment(int userId, int gridId) {
		userGoodsService.unUseEquipment(userId, gridId);
	}

	@Override
	public void useProp(int userId, int gridId) {
		userGoodsService.useProp(userId, gridId);
	}

	@Override
	public List<UserGoodsVO> updateFightProp(int userId, List<FightPropVO> fps) {
		return userGoodsService.updateFightProp(userId, fps);
	}

	@Override
	public SellGoodsResultVO sellGoods(int userId, List<SellGoodsVO> sellGoods) {
		return userGoodsService.sellGoods(userId, sellGoods);
	}

	@Override
	public int addItemCapacity(int userId) {
		return userGoodsService.addItemCapacity(userId);
	}

	@Override
	public GainAwardVO synthesisGoods(int userId, int id) {
		return userGoodsService.synthesisGoods(userId, id);
	}

	@Override
	public EqRepairRetVO repairEquipment(int userId, int gridId) {
		return userGoodsService.repairEuipment(userId, gridId);
	}

	@Override
	public List<UserBakPropVO> gainUserBakProps(int userId) {
		return userGoodsService.gainUserBakProps(userId);
	}

	@Override
	public EqRefiningVO eqRefining(int userId, int eqGridId, int propGridId) {
		return userGoodsService.eqRefining(userId, eqGridId, propGridId);
	}

	
}
