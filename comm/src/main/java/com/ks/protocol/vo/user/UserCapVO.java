package com.ks.protocol.vo.user;

import java.util.List;

import com.ks.model.user.UserCap;
import com.ks.protocol.Message;
/**
 * 用户队长
 * @author ks
 */
public class UserCapVO extends Message {

	private static final long serialVersionUID = 1L;
	
	/**用户编号*/
	private int userId;
	/**玩家名字*/
	private String playerName;
	/**玩家战魂编号*/
	private long userSoulId;
	/**战魂编号*/
	private int soulId;
	/**用户等级*/
	private int userLevel;
	/**等级*/
	private int level;
	/**强化次数*/
	private int ug;
	/**觉醒次数*/
	private int exSkillCount;
	/**装备*/
	private List<Integer> equipments;
	/**想要的东西*/
	private List<Integer> want;
	/**最后修改时间*/
	private long updateTime;
	/**竞技场积分*/
	private int arenaIntegral;
	/**当前副本*/
	private int currChapterId;
	
	public void init(UserCap o){
		this.userId = o.getUserId();
		this.userSoulId = o.getUserSoulId();
		this.soulId = o.getSoulId();
		this.userLevel = o.getUserLevel();
		this.level = o.getLevel();
		this.ug = o.getUg();
		this.exSkillCount = o.getExSkillCount();
		this.updateTime = o.getUpdateTime().getTime();
		this.playerName = o.getPlayerName();
		this.equipments = o.getEquipments();
		this.want = o.getWant();
		this.arenaIntegral=o.getArenaIntegral();
		this.currChapterId = o.getCurrChapterId();
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public long getUserSoulId() {
		return userSoulId;
	}
	public void setUserSoulId(long userSoulId) {
		this.userSoulId = userSoulId;
	}
	public int getSoulId() {
		return soulId;
	}
	public void setSoulId(int soulId) {
		this.soulId = soulId;
	}
	public int getUserLevel() {
		return userLevel;
	}
	public void setUserLevel(int userLevel) {
		this.userLevel = userLevel;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getUg() {
		return ug;
	}
	public void setUg(int ug) {
		this.ug = ug;
	}
	public int getExSkillCount() {
		return exSkillCount;
	}
	public void setExSkillCount(int exSkillCount) {
		this.exSkillCount = exSkillCount;
	}
	public long getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
	}
	public String getPlayerName() {
		return playerName;
	}
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	public List<Integer> getEquipments() {
		return equipments;
	}
	public void setEquipments(List<Integer> equipments) {
		this.equipments = equipments;
	}
	public List<Integer> getWant() {
		return want;
	}
	public void setWant(List<Integer> want) {
		this.want = want;
	}
	public int getArenaIntegral() {
		return arenaIntegral;
	}
	public void setArenaIntegral(int arenaIntegral) {
		this.arenaIntegral = arenaIntegral;
	}
	public int getCurrChapterId() {
		return currChapterId;
	}
	public void setCurrChapterId(int currChapterId) {
		this.currChapterId = currChapterId;
	}
}
