package com.ks.protocol.vo.user;

import java.util.List;

import com.ks.model.user.User;
import com.ks.protocol.Message;
/**
 * 用户
 * @author living
 *
 */
public class UserVO extends Message {
	private static final long serialVersionUID = 1L;
	/**用户id*/
	private int userId;
	/**合作方*/
	private int partner;
	/**玩家名*/
	private String playerName;
	/**等级*/
	private int level;
	/**体力*/
	private int stamina;
	/**经验*/
	private int exp;
	/**货币*/
	private int currency;
	/**游戏币*/
	private int gold;
	/**战魂容量*/
	private int soulCapacity;
	/**道具容量*/
	private int itemCapacity;
	/**累计充值总数*/
	private int totalCurrency;
	/**当前使用队伍*/
	private byte currTeamId;
	/**好友容量*/
	private int friendCapacity;
	/**最后回体时间*/
	private long lastRegainStaminaTime;
	/**想要的东西*/
	private List<Integer> want;	
	/**属性*/
	private int property;
	/**引导*/
	private int guideStep;
	/**剧情任务*/
	private int storyMission;
	/**非强制引导*/
	private int infoStep;
	/**连续登陆次数(每天一次)*/
	private int uninterruptedLoginCount;
	/**积分*/
	private int point;
	/**荣誉值*/
	private int honor;
	/**注册时间*/
	private long createTime;
	
	public int getInfoStep() {
		return infoStep;
	}

	public void setInfoStep(int infoStep) {
		this.infoStep = infoStep;
	}

	public int getProperty() {
		return property;
	}

	public void setProperty(int property) {
		this.property = property;
	}

	public int getGuideStep() {
		return guideStep;
	}

	public void setGuideStep(int guideStep) {
		this.guideStep = guideStep;
	}

	public void init(User user,List<Integer> want){
		this.userId = user.getUserId();
		this.playerName = user.getPlayerName();
		this.partner = user.getPartner();
		this.level = user.getLevel();
		this.stamina = user.getStamina();
		this.exp = user.getExp();
		this.currency = user.getCurrency();
		this.gold = user.getGold();
		this.soulCapacity = user.getSoulCapacity();
		this.itemCapacity = user.getItemCapacity();
		this.totalCurrency = user.getTotalCurrency();
		this.currTeamId = user.getCurrTeamId();
		this.friendCapacity = user.getFriendCapacity();
		this.lastRegainStaminaTime = user.getLastRegainStaminaTime().getTime();
		this.want = want;
		this.guideStep=user.getGuideStep();
		this.property=user.getProperty();
		this.storyMission=user.getStoryMission();
		this.infoStep=user.getInfoStep();
		this.uninterruptedLoginCount = user.getUninterruptedLoginCount();
		this.point = user.getPoint();
		this.honor = user.getHonor();
		this.createTime = user.getCreateTime().getTime();
	}
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getPlayerName() {
		return playerName;
	}
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	public int getPartner() {
		return partner;
	}
	public void setPartner(int partner) {
		this.partner = partner;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getExp() {
		return exp;
	}
	public void setExp(int exp) {
		this.exp = exp;
	}
	public int getCurrency() {
		return currency;
	}
	public void setCurrency(int currency) {
		this.currency = currency;
	}
	public int getGold() {
		return gold;
	}
	public void setGold(int gold) {
		this.gold = gold;
	}
	public int getSoulCapacity() {
		return soulCapacity;
	}
	public void setSoulCapacity(int soulCapacity) {
		this.soulCapacity = soulCapacity;
	}
	public int getStamina() {
		return stamina;
	}
	public void setStamina(int stamina) {
		this.stamina = stamina;
	}
	public byte getCurrTeamId() {
		return currTeamId;
	}
	public void setCurrTeamId(byte currTeamId) {
		this.currTeamId = currTeamId;
	}
	public int getItemCapacity() {
		return itemCapacity;
	}
	public void setItemCapacity(int itemCapacity) {
		this.itemCapacity = itemCapacity;
	}
	public int getFriendCapacity() {
		return friendCapacity;
	}
	public void setFriendCapacity(int friendCapacity) {
		this.friendCapacity = friendCapacity;
	}
	public long getLastRegainStaminaTime() {
		return lastRegainStaminaTime;
	}
	public void setLastRegainStaminaTime(long lastRegainStaminaTime) {
		this.lastRegainStaminaTime = lastRegainStaminaTime;
	}
	public List<Integer> getWant() {
		return want;
	}
	public void setWant(List<Integer> want) {
		this.want = want;
	}

	public int getTotalCurrency() {
		return totalCurrency;
	}

	public void setTotalCurrency(int totalCurrency) {
		this.totalCurrency = totalCurrency;
	}

	public int getStoryMission() {
		return storyMission;
	}

	public void setStoryMission(int storyMission) {
		this.storyMission = storyMission;
	}

	public int getUninterruptedLoginCount() {
		return uninterruptedLoginCount;
	}

	public void setUninterruptedLoginCount(int uninterruptedLoginCount) {
		this.uninterruptedLoginCount = uninterruptedLoginCount;
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}

	public int getHonor() {
		return honor;
	}

	public void setHonor(int honor) {
		this.honor = honor;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
}
