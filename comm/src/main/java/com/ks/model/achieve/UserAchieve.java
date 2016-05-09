package com.ks.model.achieve;

import java.io.Serializable;
import java.util.Date;

public class UserAchieve implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	
	public static final int STATE_未达成=1;
	public static final int STATE_已达成=2;
	public static final int STATE_已领取=3;

	private int userId;

	private int achieveId;
	
	private int achieveType;
	
	private int assId;
	/** 当前完成数量 */
	private int currentNum;
	/** 总目标数量 */
	private int totalNum;
	/***/
	private int state;

	public static UserAchieve create(int userId,int achieveId,int achieveType,int assId,int currentNum,int totalNum,int state){
		UserAchieve achieve=new UserAchieve();
		achieve.setUserId(userId);
		achieve.setAchieveId(achieveId);
		achieve.setCurrentNum(currentNum);
		achieve.setTotalNum(totalNum);
		achieve.setState(state);
		achieve.setAchieveType(achieveType);
		achieve.setAssId(assId);
		return achieve;
	}
	
	public int getAssId() {
		return assId;
	}

	public void setAssId(int assId) {
		this.assId = assId;
	}
	public int getAchieveType() {
		return achieveType;
	}
	public void setAchieveType(int achieveType) {
		this.achieveType = achieveType;
	}
	public int getUserId() {
		return userId;
	}
	
	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getAchieveId() {
		return achieveId;
	}

	public void setAchieveId(int achieveId) {
		this.achieveId = achieveId;
	}

	public int getCurrentNum() {
		return currentNum;
	}

	public void setCurrentNum(int currentNum) {
		this.currentNum = currentNum;
	}

	public int getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public static void main(String[] args) {
		System.out.println(new Date(1389830400000l));
	}
}
