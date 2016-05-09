package com.ks.action.logic;

import java.util.List;

import com.ks.protocol.vo.mission.UserAwardVO;
import com.ks.protocol.vo.mission.UserMissionVO;

/**
 * @author fengpeng E-mail:fengpeng_15@163.com
 * @version 创建时间：2015年1月5日 上午10:04:03
 * 类说明
 */
public interface UserMissionAction {
	/**
	 * 获取任务列表信息
	 * @param userId
	 * @return
	 */
	public List<UserMissionVO> getUserMissions(int userId);
	/**
	 * 获取任务奖励
	 * @param userId
	 * @param missionId
	 * @return
	 */
	public UserAwardVO missionAward(int userId,int missionId);
	
	/**
	 * 完成分享任务
	 */
	public void finishShareTask(int userId);

}
