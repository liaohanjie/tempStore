package com.ks.protocol.vo.mission;

import com.ks.protocol.Message;

/**
 * @author fengpeng E-mail:fengpeng_15@163.com
 * @version 创建时间：2014年12月31日 下午2:59:27
 * 类说明
 */
public class MissionAwardVO extends Message  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**主键*/
	private int id;
	/**任务id*/
	private int missionId;
	/**奖励类型*/
	private int type;
	/**关联ID*/
	private int assId;
	/**物品数量*/
	private int num;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getMissionId() {
		return missionId;
	}
	public void setMissionId(int missionId) {
		this.missionId = missionId;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getAssId() {
		return assId;
	}
	public void setAssId(int assId) {
		this.assId = assId;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	
}
