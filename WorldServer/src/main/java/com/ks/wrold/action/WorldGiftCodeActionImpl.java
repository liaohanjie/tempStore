package com.ks.wrold.action;

import java.util.List;

import com.ks.action.logic.AfficheAction;
import com.ks.action.world.WorldGiftCodeAction;
import com.ks.app.Application;
import com.ks.model.affiche.Affiche;
import com.ks.model.goods.Goods;
import com.ks.rpc.RPCKernel;

public class WorldGiftCodeActionImpl implements WorldGiftCodeAction {
	
	@Override
	public void giveCodeGift(int userId,String code, List<Goods> goods) {
		AfficheAction afficheAction = RPCKernel.getRemoteByServerType(Application.LOGIC_SERVER,AfficheAction.class);
		afficheAction.useGiftCode(userId, code, goods);
	}
	@Override
	public List<Affiche> gainAffiche(int userId) {
		AfficheAction afficheAction = RPCKernel.getRemoteByServerType(Application.LOGIC_SERVER,AfficheAction.class);
		return afficheAction.bossGainAffiche(userId);
	}

	
}
