package com.ks.model.explora;

import java.io.Serializable;
import java.util.Date;

/**
 * @author fengpeng E-mail:fengpeng_15@163.com
 * 
 * @version 创建时间：2014年8月8日 下午4:59:15
 * 
 * 探索战魂信息
 */
public class SoulExploretion implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**主键*/
	private int id;
	/**用户id*/
	private int userId;
	/**战魂id*/
	private long soulId;
	/**战魂稀有度*/
	private int soulRare;
	/** 探索时间*/
	private int hourTime;
	/**探索是否结束*/
	private int state;
	/**探索队伍Id*/
	private int teamId;
	/**探索开始时间*/
	private Date startTime;
	/**结束时间*/
	private Date endTime;
	public int getId(){
		 return id;
	}
	public void setId( int id){
		 this.id = id;
	}
	public int getUserId(){
		 return userId;
	}
	public void setUserId( int userId){
		 this.userId = userId;
	}
	public long getSoulId(){
		 return soulId;
	}
	public void setSoulId(long soulId){
		 this.soulId = soulId;
	}
	public int getSoulRare(){
		 return soulRare;
	}
	public void setSoulRare( int soulRare){
		 this.soulRare = soulRare;
	}
	public int getHourTime(){
		 return hourTime;
	}
	public void setHourTime( int hourTime){
		 this.hourTime = hourTime;
	}
	public int getState(){
		 return state;
	}
	public void setState( int state){
		 this.state = state;
	}
	public Date getStartTime(){
		 return startTime;
	}
	public void setStartTime( Date startTime){
		 this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public int getTeamId() {
		return teamId;
	}
	public void setTeamId(int teamId) {
		this.teamId = teamId;
	}

}
