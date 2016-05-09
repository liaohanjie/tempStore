package com.ks.logic.action;

import java.util.List;
import com.ks.action.logic.UserMissionAction;
import com.ks.logic.service.ServiceFactory;
import com.ks.logic.service.UserMissionService;
import com.ks.protocol.vo.mission.UserAwardVO;
import com.ks.protocol.vo.mission.UserMissionVO;

/**
 * @author fengpeng E-mail:fengpeng_15@163.com
 * @version 创建时间：2015年1月5日 上午10:08:31
 * 类说明
 */
public class UserMissionActionImpl implements UserMissionAction {
	private static UserMissionService userMissionService=ServiceFactory.getService(UserMissionService.class);
	@Override
	public List<UserMissionVO> getUserMissions(int userId) {
		return userMissionService.getUserMissions(userId);
	}

	@Override
	public UserAwardVO missionAward(int userId, int missionId) {
		return userMissionService.missionAward(userId, missionId);
	}

	@Override
	public void finishShareTask(int userId) {
		userMissionService.finishShareTask(userId);
	}
}
