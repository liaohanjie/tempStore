package com.ks.model.mission;

import java.io.Serializable;

/**
 * @author fengpeng E-mail:fengpeng_15@163.com
 * @version 创建时间：2014年12月31日 下午2:34:59
 * 类说明
 */
public class Mission implements Serializable {
	
	public static final int MISSION_TYPE_每日任务=1;
	public static final int MISSION_TYPE_收集奖励=2;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**任务id*/
	private int missionId;
	/**等级限制*/
	private int level;
	/**奖励数量*/
	private int num;
	/**任务类型*/
	private int missionType;
	public int getMissionId() {
		return missionId;
	}
	public void setMissionId(int missionId) {
		this.missionId = missionId;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public int getMissionType() {
		return missionType;
	}
	public void setMissionType(int missionType) {
		this.missionType = missionType;
	}

}
