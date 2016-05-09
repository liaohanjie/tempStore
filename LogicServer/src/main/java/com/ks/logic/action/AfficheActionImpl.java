package com.ks.logic.action;

import java.util.List;

import com.ks.action.logic.AfficheAction;
import com.ks.logic.service.AfficheService;
import com.ks.logic.service.ServiceFactory;
import com.ks.model.affiche.Affiche;
import com.ks.model.goods.Goods;
import com.ks.protocol.vo.affiche.AfficheVO;
import com.ks.protocol.vo.goods.GainAwardVO;

public class AfficheActionImpl implements AfficheAction {

	private static final AfficheService afficheService = ServiceFactory.getService(AfficheService.class);

	@Override
	public List<AfficheVO> gainAffiche(int userId) {
		return afficheService.gainAffiche(userId);
	}

	@Override
	public void deleteAffiche(int id, int userId) {
		afficheService.deleteAffiche(id, userId);
	}

	@Override
	public GainAwardVO getAfficheGoods(int userId, int id) {
		return afficheService.getAfficheGoods(userId, id);
	}

	@Override
	public void useGiftCode(int userId, String code, List<Goods> goodsList) {
		afficheService.useGiftCode(userId, code, goodsList);
	}

	@Override
	public List<Affiche> bossGainAffiche(int userId) {
		return afficheService.queryAffiche(userId);
	}
	@Override
	public void addAffiche(Affiche a) {
		afficheService.addAffiche(a);
	}


	@Override
	public GainAwardVO getAllAfficheGoods(int userId) {
		return afficheService.getAllAfficheGoods(userId);
	}

	@Override
	public void viewAllAffiche(int userId, List<Integer> ids) {
		afficheService.viewAllAffiche(userId, ids);
	}

	@Override
	public void cleanAffiche() {
		afficheService.cleanAffiche();
	}
}
