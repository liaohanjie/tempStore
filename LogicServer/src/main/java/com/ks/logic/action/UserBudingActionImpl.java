package com.ks.logic.action;

import java.util.List;

import com.ks.action.logic.UserBudingAction;
import com.ks.logic.service.ServiceFactory;
import com.ks.logic.service.UserBudingService;
import com.ks.protocol.vo.buding.CollectResultVO;
import com.ks.protocol.vo.buding.LevelUpBudingResultVO;
import com.ks.protocol.vo.buding.UserBudingVO;

public class UserBudingActionImpl implements UserBudingAction {
	
	private static final UserBudingService userBudingService = ServiceFactory.getService(UserBudingService.class);
	
	@Override
	public List<UserBudingVO> gainUserBudings(int userId) {
		return userBudingService.gainUserBudings(userId);
	}

	@Override
	public LevelUpBudingResultVO levelUpBuding(int userId, int budingId,
			int gold) {
		return userBudingService.levelUpBuding(userId, budingId, gold);
	}

	@Override
	public CollectResultVO collectBuding(int userId, int budingId, int count) {
		return userBudingService.collectBuding(userId, budingId, count);
	}
	
	@Override
	public CollectResultVO collectBuding(int userId, int budingId) {
		return userBudingService.collectBuding(userId, budingId);
	}

}
