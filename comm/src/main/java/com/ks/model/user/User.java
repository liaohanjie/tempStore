package com.ks.model.user;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.ks.model.goods.UserBakProp;
import com.ks.model.mission.UserMission;
/**
 * 用户
 * @author ks
 *
 */
public final class User implements Serializable {

	private static final long serialVersionUID = -4899178985355267811L;
	/**最大等级*/
	public static final int MAX_LEVEL = 100;
	/**可购买的最大战魂仓库容量*/
	public static final int MAX_SOUL_CAPACITY = 450;
	/**可购买的最大仓库容量*/
	public static final int MAX_ITEM_CAPACITY = 430;
	/**可购买的最大好友容量*/
	public static final int MAX_FRIEND_CAPACITY = 450;
	/**恢复体力时间*/
	public static final long REGAIN_STAMINA_TIME = 5*60*1000L;
	
	public static final int PROPERTY_起名=0b1;
	public static final int PROPERTY_选战魂=0b10;
	public static final int PROPERTY_成长基金=0b100;
	
	public static final int GUIDE_STEP1_播放GC=10100;
	public static final int GUIDE_STEP2_第一次战斗=10200;
	public static final int GUIDE_STEP3_玩家起名=10300;
	public static final int GUIDE_STEP4_角色选择=10400;
	public static final int GUIDE_STEP5_二次战斗=10500;
	public static final int GUIDE_STEP6_召唤战魂=10600;
	public static final int GUIDE_STEP7_编队指引=10700;
	public static final int GUIDE_STEP8_战斗引导=10800;
	public static final int GUIDE_STEP9_三次战斗=10900;
	public static final int GUIDE_STEP10_送经验宝=10950;
	public static final int GUIDE_STEP11_强化=11000;
	public static final int GUIDE_STEP12_送进化精灵=11050;
	public static final int GUIDE_STEP13_进化=11100;
	public static final int GUIDE_STEP14_进入关卡=11200;
	
	public static final int GUIDE_STEP20_道具屋=20180;
	public static final int GUIDE_STEP21_锻造屋送材料=20790;
	public static final int GUIDE_STEP21_锻造屋=20800;
	public static final int GUIDE_STEP22_指引END=21100;
	
	public static final int INFO_STEP_家园按钮=0b1; //1
	public static final int INFO_STEP_药剂房=0b10; //2
	public static final int INFO_STEP_竞技场=0b100; //4w
	
	public static final int USER_ID_START=100000; //4
	public static final int USER_ID_END=500000; //4
	public static final Integer USER_ID_好友机器人=100001; //4
	public static final String USER_NAME_机器人="我是玩家";
	
	public static final int USER_扩展战魂仓库价格=20;
	public static final int USER_恢复体力价格=40;
	public static final int USER_恢复竞技点价格=10;
	public static final int USER_提升好友上线价格=20;
	public static final int USER_扩展家园上线价格=20;
	public static final int USER_CALL_召唤战魂一次=248;
	public static final int USER_CALL_召唤战魂十次=2300;
	public static final int USER_新手赠送 = USER_CALL_召唤战魂一次;
	/**剧情结束*/
	public static final int STORY_MISSION_END=54;
	/**新手可先择的战魂*/
	public static final int[] GUIDE_SOULS=new int[]{1010001,1010006,1010011,1010016};
	/***/
	//public static final int[] VIP_BUY_STAMINA_MONEY=new int[]{40,40,70,70,100,200,200,200,200,200,200,200};
	
	/**用户id*/
	private int userId;
	/**用户名*/
	private String username;
	/**合作方*/
	private int partner;
	/**玩家名*/
	private String playerName;
	/**等级*/
	private int level=1;
	/**体力*/
	private int stamina;
	/**经验*/
	private int exp;
	/**货币*/
	private int currency;
	/**游戏币*/
	private int gold;
	/**购买战魂容量*/
	private int soulCapacity=0;
	/**购买仓库容量*/
	private int itemCapacity=0;
	/**累计充值总数*/
	private int totalCurrency;
	/**当前使用队伍*/
	private byte currTeamId=1;
	/**购买的好友容量*/
	private int friendCapacity;
	/**最后登录时间*/
	private Date lastLoginTime;
	/**最后下线时间*/
	private Date lastLogoutTime;
	/**创建时间*/
	private Date createTime;
	/**修改时间*/
	private Date updateTime;
	/**最后恢复体力时间*/
	private Date lastRegainStaminaTime;
	/**属性*/
	private int property;
	/**新手引导*/
	private int guideStep;
	/**剧情任务*/
	private int storyMission;
	/**非强制引导*/
	private int infoStep;
	/**副本道具-钥匙*/
	private Map<Integer,UserBakProp> bakProps;
	/**用户任务*/
	private Map<Integer,UserMission> missions = new HashMap<Integer, UserMission>();
	/**连续登陆次数(每天一次)*/
	private int uninterruptedLoginCount;
	/**首次充值货币(魂钻)*/
	private int firstCurrency;
	/**积分*/
	private int point;
	/**荣誉值*/
	private int honor;
	/**封号时间*/
	private Date banAccountTime;
	/**禁言时间*/
	private Date banChatTime;
	
	public int getInfoStep() {
		return infoStep;
	}
	public void setInfoStep(int infoStep) {
		this.infoStep = infoStep;
	}
	public int getGuideStep() {
		return guideStep;
	}
	public void setGuideStep(int guideStep) {
		this.guideStep = guideStep;
	}
	public int getProperty() {
		return property;
	}
	public void setProperty(int property) {
		this.property = property;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPlayerName() {
		return playerName;
	}
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
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
	public Date getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	public Date getLastLogoutTime() {
		return lastLogoutTime;
	}
	public void setLastLogoutTime(Date lastLogoutTime) {
		this.lastLogoutTime = lastLogoutTime;
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
	public Date getLastRegainStaminaTime() {
		return lastRegainStaminaTime;
	}
	public void setLastRegainStaminaTime(Date lastRegainStaminaTime) {
		this.lastRegainStaminaTime = lastRegainStaminaTime;
	}
	public int getTotalCurrency() {
		return totalCurrency;
	}
	public void setTotalCurrency(int totalCurrency) {
		this.totalCurrency = totalCurrency;
	}
	public int getHonor() {
		return honor;
	}
	public void setHonor(int honor) {
		this.honor = honor;
	}
	public int getStoryMission() {
		return storyMission;
	}
	public void setStoryMission(int storyMission) {
		this.storyMission = storyMission;
	}
	public Map<Integer, UserBakProp> getBakProps() {
		return bakProps;
	}
	public void setBakProps(Map<Integer, UserBakProp> bakProps) {
		this.bakProps = bakProps;
	}
	public Map<Integer, UserMission> getMissions() {
		return missions;
	}
	public void setMissions(Map<Integer, UserMission> missions) {
		this.missions = missions;
	}
	public int getUninterruptedLoginCount() {
		return uninterruptedLoginCount;
	}
	public void setUninterruptedLoginCount(int uninterruptedLoginCount) {
		this.uninterruptedLoginCount = uninterruptedLoginCount;
	}
	public int getFirstCurrency() {
		return firstCurrency;
	}
	public void setFirstCurrency(int firstCurrency) {
		this.firstCurrency = firstCurrency;
	}
	public int getPoint() {
		return point;
	}
	public void setPoint(int point) {
		this.point = point;
	}
	public Date getBanAccountTime() {
		return banAccountTime;
	}
	public void setBanAccountTime(Date banAccountTime) {
		this.banAccountTime = banAccountTime;
	}
	public Date getBanChatTime() {
		return banChatTime;
	}
	public void setBanChatTime(Date banChatTime) {
		this.banChatTime = banChatTime;
	}
}
