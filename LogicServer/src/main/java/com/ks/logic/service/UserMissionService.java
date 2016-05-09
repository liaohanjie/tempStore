package com.ks.logic.service;

import java.util.List;

import com.ks.access.Transaction;
import com.ks.model.mission.Mission;
import com.ks.model.mission.MissionAward;
import com.ks.model.mission.MissionCondition;
import com.ks.model.mission.UserMission;
import com.ks.model.user.User;
import com.ks.model.user.UserStat;
import com.ks.protocol.vo.mission.UserAwardVO;
import com.ks.protocol.vo.mission.UserMissionVO;

/**
 * @author fengpeng E-mail:fengpeng_15@163.com
 * @version 创建时间：2014年12月30日 下午3:09:18
 * 类说明
 */
public interface UserMissionService {
	/**
	 * 做任务
	 * @param user
	 * @param type
	 * @param assId
	 * @param num
	 */
	@Transaction
	void finishMissionCondition(User user,int type,int assId,int num);
	/**
	 * 任务条件列表
	 * @return
	 */
	List<MissionCondition> queryMissionCondition();

	/**
	 * 基础任务
	 * @return
	 */
	List<Mission> queryMission();
	/**
	 * 任务奖励
	 * @return
	 */
	List<MissionAward> queryMissionAward();
	/***
	 * 初始化任务
	 * @param userId
	 */
	@Transaction
	void initUserMission(int userId);
	/**
	 * 任务奖励
	 * @param userId
	 * @param missionId
	 * @return
	 */
	@Transaction
	public UserAwardVO missionAward(int userId, int missionId);
	/***
	 * 任务列表
	 * @param userId
	 */
	@Transaction
	List<UserMissionVO> getUserMissions(int userId);
	
	/**
	 * 检查任务条件是否满足
	 * @param userMission
	 * @return
	 */
	boolean checkMissionCoindition(UserMission userMission);
	
	/**
	 * 完成分享任务
	 * @param userId
	 */
	void finishShareTask(int userId);
	
	/**
	 * 刷新每日任务
	 * @param userStat
	 * @return
	 */
	List<UserMission> freshDayMission(UserStat userStat);
	
	/**
	 * 刷新每日任务
	 * @param userId
	 * @return
	 */
	List<UserMission> freshDayMission(int userId);
}
