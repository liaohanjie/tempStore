package com.ks.logic.service;

import java.util.List;

import com.ks.access.Transaction;
import com.ks.model.user.User;
import com.ks.model.user.UserCap;
import com.ks.model.user.UserSoul;
import com.ks.model.user.UserTeam;
import com.ks.protocol.vo.soul.UserSoulInfoVO;
import com.ks.protocol.vo.user.UserCapVO;
import com.ks.protocol.vo.user.UserSoulTeamVO;
import com.ks.protocol.vo.user.UserTeamVO;

/**
 * 用户队伍
 * @author ks
 */
public interface UserTeamService {
	/**
	 * 初始化用户队伍
	 * @param user
	 * @param souls
	 * @return 
	 */
	@Transaction
	List<UserTeam> initUserTeam(User user,List<UserSoul> souls);
	/**
	 * 清除用户队伍缓存
	 * @param userId
	 */
	@Transaction
	void clearUserTeamCache(int userId);
	/**
	 * 初始化用户队长
	 * @param user 用户编号
	 * @param userSoul 战魂编号
	 */
	void initUserCap(User user,UserSoul userSoul);
	
	/**
	 * 修改用户队伍
	 * @param userId 用户编号
	 * @param teams 要修改的队伍
	 */
	void updateUserTeam(int userId,List<UserTeamVO> teams,byte currTeamId);
	/**
	 * 检查用户队伍(cost检查)
	 * @param user 用户
	 * @param souls 队伍里的战魂
	 */
	void checkUserTeam(User user,List<UserSoulInfoVO> souls);
	
	/**
	 * 获得用户队伍
	 * @param userId 用户编号
	 * @param teamId 用户队伍编号
	 * @return 用户队伍
	 */
	UserTeam getExistUserTeamCache(int userId,byte teamId);
	/**
	 * 修改用户队长
	 * @param cap 用户队长
	 * @param userSoul 换成的队长
	 * @param equipments 身上的装备
	 */
	void updateUserCap(UserCap cap, UserSoul userSoul,List<Integer> equipments);
	
	/**
	 * 获得用户队伍
	 * @param userId 用户编号
	 * @param teamId 用户队伍编号
	 * @return 用户队伍
	 */
	UserTeam getExistUserTeam(int userId,byte teamId);
	
	/**
	 * 查找用户队伍信息
	 * @param userId 用户编号
	 * @return
	 */
	UserSoulTeamVO findUserCurrentTeam(int userId);
	
	/**
	 * 查找玩家队长
	 * @param userId
	 * @return
	 */
	UserCap findUserCapCache(int userId);
	
	/**
	 * 查找玩家队长
	 * @param userId
	 * @return
	 */
	UserCapVO findUserCapVOCache(int userId);
}
