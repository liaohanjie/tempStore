package com.ks.protocol.vo.explora;

import com.ks.model.explora.SoulExploretion;
import com.ks.protocol.Message;

/**
 * @author fengpeng E-mail:fengpeng_15@163.com
 * 
 * @version 创建时间：2014年8月8日 下午4:59:15
 * 
 * 探索战魂信息
 */
public class SoulExploretionVO extends Message {

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
	/**探索队伍*/
	private int teamId;
	/**探索开始时间*/
	private long startTime;
	/**探索开始时间*/
	private long endTime;
	/**货币*/
	private int gold;
	
	public void init(SoulExploretion o){
		this.id=o.getId();
		this.userId=o.getUserId();
		this.soulId=o.getSoulId();
		this.soulRare=o.getSoulRare();
		this.hourTime=o.getHourTime();
		this.state=o.getState();
		this.teamId=o.getTeamId();
		this.startTime=o.getStartTime().getTime();
		this.endTime=o.getEndTime().getTime();
	}
	@Override
	public String toString() {
		return "SoulExploretionVO [id=" + id + ", userId=" + userId
				+ ", soulId=" + soulId + ", soulRare=" + soulRare
				+ ", hourTime=" + hourTime + ", state=" + state + ", teamId="
				+ teamId + ", startTime=" + startTime + ", endTime=" + endTime
				+ "]";
	}
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
	public void setSoulId( long soulId){
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

	public long getEndTime() {
		return endTime;
	}
	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}
	public long getStartTime() {
		return startTime;
	}
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	public int getTeamId() {
		return teamId;
	}
	public void setTeamId(int teamId) {
		this.teamId = teamId;
	}
	public int getGold() {
		return gold;
	}
	public void setGold(int currency) {
		this.gold = currency;
	}

}
