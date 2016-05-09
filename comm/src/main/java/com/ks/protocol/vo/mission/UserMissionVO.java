package com.ks.protocol.vo.mission;

import java.util.List;

import com.ks.protocol.Message;

/**
 * @author fengpeng E-mail:fengpeng_15@163.com
 * @version 创建时间：2014年12月30日 下午3:43:39
 * 类说明
 */
public class UserMissionVO extends Message {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**用户id*/
	private int userId;
	/**任务id*/
	private int missionId;
	/**任务条件*/
	private  List<ConditionVO> condition;
	/**是否已领奖*/
	private int state;
	
	private List<MissionAwardVO> awardList;

	public static UserMissionVO createUserMission(int userId, int missionId, List<ConditionVO> condition,
			int state, List<MissionAwardVO> awardList) {
		UserMissionVO um = new UserMissionVO();
		um.userId = userId;
		um.missionId = missionId;
		um.condition = condition;
		um.state = state;
		um.awardList = awardList;
		return um;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getMissionId() {
		return missionId;
	}
	public void setMissionId(int missionId) {
		this.missionId = missionId;
	}
	public List<ConditionVO> getCondition() {
		return condition;
	}
	public void setCondition(List<ConditionVO> condition) {
		this.condition = condition;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	
	public List<MissionAwardVO> getAwardList() {
		return awardList;
	}
	public void setAwardList(List<MissionAwardVO> awardList) {
		this.awardList = awardList;
	}
	

}
