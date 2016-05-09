package com.ks.logic.action;

import com.ks.action.logic.AthleticsInfoAction;
import com.ks.logic.service.AthleticsInfoService;
import com.ks.logic.service.ServiceFactory;
import com.ks.protocol.vo.pvp.AthleticsInfoModelVO;
import com.ks.protocol.vo.pvp.AthleticsInfoVO;

public class AthleticsInfoActionImpl implements AthleticsInfoAction {
	private static AthleticsInfoService athleticsInfoService = ServiceFactory.getService(AthleticsInfoService.class);
	@Override
	public AthleticsInfoModelVO getAthleticsInfo(int userId) {
		return athleticsInfoService.queryAthleticsInfoBytotalIntegral(userId);
	}

	@Override
	public AthleticsInfoVO getMatchUser(int userId) {
		return athleticsInfoService.queryMatchUserIds(userId);
	}

	@Override
	public AthleticsInfoVO enterArnea(int userId) {
		return athleticsInfoService.enterArnea(userId);
	}

	@Override
	public AthleticsInfoVO startArneaPK(int attackerId, int defenderId) {
		return athleticsInfoService.startArneaPK(attackerId, defenderId);
	}

	@Override
	public int regainAthleticsPoint(int userId) {
		return athleticsInfoService.regainAthleticsPoint(userId);
	}

}
