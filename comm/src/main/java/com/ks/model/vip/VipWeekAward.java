package com.ks.model.vip;

import java.io.Serializable;
/**
 * 
 * @author fengpeng E-mail:fengpeng_15@163.com
 *
 * @version 创建时间：2014年9月1日 下午3:30:31
 */
public class VipWeekAward implements Serializable {

	private static final long serialVersionUID = 1L;
	/** vip等级 */
	private int vipGrade;
	/** 奖励类型 */
	private int type;
	/** 关联ID */
	private int assId;
	/** 物品数量 */
	private int num;
	/**等级*/
	private int level;
	public int getVipGrade() {
		return vipGrade;
	}

	public void setVipGrade(int vipGrade) {
		this.vipGrade = vipGrade;
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

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

}