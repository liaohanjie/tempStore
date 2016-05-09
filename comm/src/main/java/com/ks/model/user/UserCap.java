package com.ks.model.user;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
/**
 * 玩家队长
 * @author ks
 */
public class UserCap implements Serializable {

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
	/**竞技场积分*/
	private int arenaIntegral;
	/**创建时间*/
	private Date createTime;
	/**修改时间*/
	private Date updateTime;
	/**当前副本*/
	private int currChapterId;
	
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
	public int getUserLevel() {
		return userLevel;
	}
	public void setUserLevel(int userLevel) {
		this.userLevel = userLevel;
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
	@Override
	public String toString() {
		return "UserCap [userId=" + userId + ", playerName=" + playerName
				+ ", userSoulId=" + userSoulId + ", soulId=" + soulId
				+ ", userLevel=" + userLevel + ", level=" + level + ", ug="
				+ ug + ", exSkillCount=" + exSkillCount + ", equipments="
				+ equipments + ", want=" + want + ", arenaIntegral="
				+ arenaIntegral + ", createTime=" + createTime
				+ ", updateTime=" + updateTime + "]";
	}
	
}
