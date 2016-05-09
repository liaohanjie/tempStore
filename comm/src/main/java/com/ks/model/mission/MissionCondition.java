package com.ks.model.mission;

import java.io.Serializable;

/**
 * @author fengpeng E-mail:fengpeng_15@163.com
 * @version 创建时间：2014年12月30日 下午3:24:38 类说明
 */
public class MissionCondition implements Serializable {

	public static final int TYPE_金币数量 = 1;
	public static final int TYPE_闯关次数_不限关卡 = 2;
	public static final int TYPE_闯关次数_限某一关卡 = 3;
	public static final int TYPE_怪物击杀数量_不限怪物 = 4;
	public static final int TYPE_怪物击杀数量_限某一怪物 = 5;
	public static final int TYPE_消费钻数量 = 6;
	public static final int TYPE_家园收获 = 7;
	public static final int TYPE_药剂制造_不限药剂 = 8;
	public static final int TYPE_装备制造_不限装备 = 9;
	public static final int TYPE_战魂强化 = 10;
	public static final int TYPE_战魂进化 = 11;
	public static final int TYPE_竞技场次数 = 12;
	public static final int TYPE_好友赠送 = 13;
	public static final int TYPE_探索次数 = 14;
	public static final int TYPE_黄金月卡 = 15;
	public static final int TYPE_钻石月卡 = 16;
	public static final int TYPE_分享 = 17;
	public static final int TYPE_开服活动任务 = 18;
	public static final int TYPE_点金手 = 19;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 主见Id */
	private int id;
	/** 任务Id */
	private int missionId;
	/** 任务类型 */
	private int type;
	/** 关联id */
	private int assId;
	/** 数量 */
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
