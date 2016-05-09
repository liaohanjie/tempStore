package com.ks.protocol.vo.pvp;

import java.util.List;

import com.ks.model.pvp.AthleticsInfo;
import com.ks.protocol.Message;
import com.ks.protocol.vo.fight.FightVO;
import com.ks.protocol.vo.user.UserCapVO;

public class AthleticsInfoVO extends Message {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/***/
	private int userId;
	/**竞技点*/
	private int athleticsPoint;
	/**总积分*/
	private int totalIntegral;
	/** 胜场*/
	private int wins;
	/**输的场次*/
	private int lose;
	/**当前连胜场次*/
	private int streakWin;
	/**最长连胜场次*/
	private int highestWinStreak;
	/**已领将称号*/
	private List<Integer> awardTitle;
	/**上次回竞技点时间*/
	private long lastBackTime;
	/**战斗信息*/
	private FightVO fightVO;
	/**队长信息*/
	private UserCapVO userCap;
	public void init(AthleticsInfo o){
		this.athleticsPoint=o.getAthleticsPoint();
		this.awardTitle=o.getAwardTitle();
		this.highestWinStreak=o.getHighestWinStreak();
		this.lastBackTime=o.getLastBackTime().getTime();
		this.lose=o.getLose();
		this.streakWin=o.getStreakWin();
		this.totalIntegral=o.getTotalIntegral();
		this.userId=o.getUserId();
		this.wins=o.getWins();
	}
	public int getAthleticsPoint() {
		return athleticsPoint;
	}
	public void setAthleticsPoint(int athleticsPoint) {
		this.athleticsPoint = athleticsPoint;
	}
	public int getUserId(){
		 return userId;
	}
	public void setUserId( int userId){
		 this.userId = userId;
	}
	public int getTotalIntegral(){
		 return totalIntegral;
	}
	public void setTotalIntegral( int totalIntegral){
		 this.totalIntegral = totalIntegral;
	}
	public int getWins(){
		 return wins;
	}
	public void setWins( int wins){
		 this.wins = wins;
	}
	public int getLose(){
		 return lose;
	}
	public void setLose( int lose){
		 this.lose = lose;
	}
	public int getStreakWin(){
		 return streakWin;
	}
	public void setStreakWin( int streakWin){
		 this.streakWin = streakWin;
	}
	
	public long getLastBackTime() {
		return lastBackTime;
	}
	public void setLastBackTime(Long lastBackTime) {
		this.lastBackTime = lastBackTime;
	}
	public List<Integer> getAwardTitle() {
		return awardTitle;
	}
	public void setAwardTitle(List<Integer> awardTitle) {
		this.awardTitle = awardTitle;
	}
	public int getHighestWinStreak() {
		return highestWinStreak;
	}
	public void setHighestWinStreak(int highestWinStreak) {
		this.highestWinStreak = highestWinStreak;
	}
	public FightVO getFightVO() {
		return fightVO;
	}
	public void setFightVO(FightVO fightVO) {
		this.fightVO = fightVO;
	}
	public UserCapVO getUserCap() {
		return userCap;
	}
	public void setUserCap(UserCapVO userCap) {
		this.userCap = userCap;
	}
	


}
