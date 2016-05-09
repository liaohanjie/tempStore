package com.ks.model.mission;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author fengpeng E-mail:fengpeng_15@163.com
 * @version 创建时间：2014年12月30日 下午3:43:39
 * 类说明
 */
public class UserMission implements Serializable {

	/**
	 * 
	 */
	public static final int STASTE_未领奖=0;
	public static final int STASTE_已领奖=1;
	private static final long serialVersionUID = 1L;
	
	/**用户id*/
	private int userId;
	/**任务id*/
	private int missionId;
	/**任务条件*/
	private  List<Condition> condition;
	/**是否已领奖*/
	private int state;
	
	private List<MissionAward> awardList;
	/**创建时间*/
	private Date createTime;
	/**更新时间*/
	private Date updateTime;
	/**是否修改过*/
	private boolean update;
	
	public static UserMission createUserMission(int userId, int missionId, List<Condition> condition,
			int state, List<MissionAward> awardList, Date createTime,
			Date updateTime, boolean update) {
		UserMission um = new UserMission();
		um.userId = userId;
		um.missionId = missionId;
		um.condition = condition;
		um.state = state;
		um.awardList = awardList;
		um.createTime = createTime;
		um.updateTime = updateTime;
		um.update = update;
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
	public List<Condition> getCondition() {
		return condition;
	}
	public void setCondition(List<Condition> condition) {
		this.condition = condition;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public List<MissionAward> getAwardList() {
		return awardList;
	}
	public void setAwardList(List<MissionAward> awardList) {
		this.awardList = awardList;
	}
	public boolean isUpdate() {
		return update;
	}
	public void setUpdate(boolean update) {
		this.update = update;
	}

}
