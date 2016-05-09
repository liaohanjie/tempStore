package com.ks.logic.action;

import java.util.List;

import com.ks.action.logic.UserTeamAction;
import com.ks.logic.service.ServiceFactory;
import com.ks.logic.service.UserTeamService;
import com.ks.protocol.vo.user.UserCapVO;
import com.ks.protocol.vo.user.UserSoulTeamVO;
import com.ks.protocol.vo.user.UserTeamVO;

public class UserTeamActionImpl implements UserTeamAction {
	
	private static final UserTeamService service = ServiceFactory.getService(UserTeamService.class);
	
	@Override
	public void updateUserTeam(int userId, List<UserTeamVO> teams,byte currTeamId) {
		service.updateUserTeam(userId, teams,currTeamId);
	}

	@Override
    public UserSoulTeamVO findUserCurrentTeam(int userId) {
	    return service.findUserCurrentTeam(userId);
    }

	@Override
    public UserCapVO findUserCap(int userId) {
	    return service.findUserCapVOCache(userId);
    }
}
