package com.ks.logic.action;

import java.util.List;

import com.ks.action.logic.UserSoulAction;
import com.ks.logic.service.ServiceFactory;
import com.ks.logic.service.UserSoulService;
import com.ks.protocol.vo.soul.CallSoulResultVO;
import com.ks.protocol.vo.soul.UserSoulInfoVO;
import com.ks.protocol.vo.soul.UserSoulMapVO;
import com.ks.protocol.vo.soul.UserSoulOptResultVO;
import com.ks.protocol.vo.user.UserSoulVO;

public class UserSoulActionImpl implements UserSoulAction {
	
	private static final UserSoulService service = ServiceFactory.getService(UserSoulService.class);
	
	@Override
	public UserSoulOptResultVO strengUserSoul(int userId, long userSoulId,
			List<Long> soulIds) {
		return service.strengUserSoul(userId, userSoulId, soulIds);
	}

	@Override
	public UserSoulOptResultVO soulEvolution(int userId, long userSoulId,
			List<Long> soulIds) {
		return service.soulEvolution(userId, userSoulId, soulIds);
	}

	@Override
	public int sellSoul(int userId, List<Long> userSoulIds) {
		return service.sellSoul(userId, userSoulIds);
	}

	@Override
	public CallSoulResultVO callSoul(int userId,int type,int num) {
		return service.callSoul(userId,type,num);
	}

	@Override
	public UserSoulInfoVO gainUserSoulInfo(int userId) {
		return service.gainUserSoulCapInfo(userId);
	}

	@Override
	public int addSoulCapacity(int userId) {
		return service.addSoulCapacity(userId);
	}

	@Override
	public List<UserSoulMapVO> queryUserSoulMap(int userId) {		
		return service.queryUserSoulMap(userId);
	}

	@Override
	public UserSoulOptResultVO reShapeSoul(int userId, List<Long> userSoulIds) {
		return service.reShapeSoul(userId, userSoulIds);
	}
	@Override
	public void updateSoulSafe(int userId, long userSoulId, boolean safe) {
		service.updateSoulSafe(userId, userSoulId, safe);
	}
	@Override
	public UserSoulVO getGuideStrengSoul(int userId, int choose) {
		return service.getGuideStrengSoul(userId, choose);
	}


	@Override
	public UserSoulOptResultVO guideSoulEvolution(int userId, long userSoulId) {
		return service.guideSoulEvolution(userId, userSoulId);
	}

}
