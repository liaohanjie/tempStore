package com.ks.action.logic;

import java.util.List;

import com.ks.protocol.vo.user.UserCapVO;
import com.ks.protocol.vo.user.UserSoulTeamVO;
import com.ks.protocol.vo.user.UserTeamVO;

public interface UserTeamAction {

	/**
	 * 修改用户队伍
	 * @param userId 用户编号
	 * @param teams 要修改的队伍
	 */
	void updateUserTeam(int userId,List<UserTeamVO> teams,byte currTeamId);
	
	/**
	 * 查找用户当前 Team
	 * @param userId
	 * @return
	 */
	UserSoulTeamVO findUserCurrentTeam(int userId);

	/**
	 * 查找队长信息
	 * @param userId
	 * @return
	 */
	UserCapVO findUserCap(int userId);
}
