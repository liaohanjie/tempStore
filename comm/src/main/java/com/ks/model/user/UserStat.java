package com.ks.model.user;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
/**
 * 用户统计
 * @author ks
 */
public class UserStat implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**最大友情点*/
	public static final int MAX_FRIENDLY_POINT = 10000;
	/**最大购买体力次数*/
	public static final int MAX_BUY_STAMINA_COUNT = 2;
	/**扫荡获得最大战魂个数*/
	public static final int MAX_SWEEP_SOUL_NUM= 15;
	
	
	public static final int MAX_乱入_次数=10;
	public static final String SPLIT="_";
	public static final int PROPERTY_第一次领取体力=0b1;
	public static final int PROPERTY_第二次领取体力=0b10;
	
	/**(首充)首次充值标记*/
	public static final int PROP_MARK_FIRST_RECHARGE = 0x01;
	
	/**用户编号*/
	private int userId;
	/**友情点*/
	private int friendlyPoint;
	/**使用过的好友*/
	private List<Integer> useFriend;
	/**赠送过礼物的好友*/
	private List<Integer> handselFriend;
	/**购买体力次数*/
	private int buyStaminaCount;
	/**创建时间*/
	private Date createTime;
	/**修改时间*/
	private Date updateTime;
	/**累计登录*/
	private int totalLogin;	
	/**已领取的登录奖励*/
	private String onTimeLoginAward;	
	/**已领取的累计登录奖励*/
	private String totalLoginAward;
	/**vip已领奖时间*/
	private Date vipAwardTime;
	/**vip已扫荡次数*/
	private int sweepCount;	
	/**vip已购买扫荡次数*/
	private  int buySweepCount;
	/**今日乱入总次数**/
	private int chpaterJoinCount;
	/**已领取的等级成长基金奖励*/
	private String growthGift;
	/**最后领取指定时间登录奖励的时间*/
	private Date onTimeGiftTime;
	/**充值活动期间买魂币总数*/
	private int acChageTotal;
	/**重置任务时间*/
	private Date refreshMissionDate;
	/**今天已经合作胜利的好友*/
	private List<Integer> winFriend;
	/**每天领体力次数*/
	private int sendStamainCount;
	/**立即结束探索次数*/
	private int buyExploretionCount;
	/**连续登陆次数礼包领取标示*/
	private String continuousLoginGiftMark;
	/**冲级礼包领取标示*/
	private String levelGiftMark;
	/**商品每天购买次数标记(例子: ID_次数,)*/
	private String productDayMark;
	/**限时活动充值(单位：魂钻)*/
	private int activityRechargeCurrency;
	/**点金手当天已使用次数，0点置0*/
	private int coinHandNum;
	/**当天复活次数*/
	/*private int resurrectionNum;*/
	/**友情点召唤次数*/
	private int callSoulFriendPointNum;
	/**魂钻召唤次数*/
	private int callSoulCurrencyNum;
	/**每日充值累计*/
	private int dayTotalCurrency;
	/**活动内连续充值次数(连续充值活动,按天算连续)*/
	private int activityContinuousRechargeCount;
	/**最后充值时间*/
	private Date lastRechargeTime;
	/**活动连续领取记录(二进制标记)*/
	private int activityContinuousRechargeGetMark;
	/**活动内累计消费额*/
	private int activityTotalCostCurrency;
	/**在线礼包领取次数(按天)*/
	private int onlineGiftGetCount;
	/**免费战魂召唤时间*/
	private Date freeCallSoulTime;
	/**神秘商店最后刷新时间*/
	private Date mysteryShopFreshTime;
	
	public int getSendStamainCount() {
		return sendStamainCount;
	}
	public void setSendStamainCount(int sendStamainCount) {
		this.sendStamainCount = sendStamainCount;
	}
	public List<Integer> getWinFriend() {
		return winFriend;
	}
	public void setWinFriend(List<Integer> winFriend) {
		this.winFriend = winFriend;
	}
	public Date getRefreshMissionDate() {
		return refreshMissionDate;
	}
	public void setRefreshMissionDate(Date refreshMissionDate) {
		this.refreshMissionDate = refreshMissionDate;
	}
	public Date getOnTimeGiftTime() {
		return onTimeGiftTime;
	}
	public int getAcChageTotal() {
		return acChageTotal;
	}

	public void setAcChageTotal(int acChageTotal) {
		this.acChageTotal = acChageTotal;
	}

	public void setOnTimeGiftTime(Date onTimeGiftTime) {
		this.onTimeGiftTime = onTimeGiftTime;
	}

	public String getGrowthGift() {
		return growthGift;
	}

	public void setGrowthGift(String growthGift) {
		this.growthGift = growthGift;
	}

	public Date getVipAwardTime() {
		return vipAwardTime;
	}
	public void setVipAwardTime(Date vipAwardTime) {
		this.vipAwardTime = vipAwardTime;
	}
	public int getChpaterJoinCount() {
		return chpaterJoinCount;
	}
	public void setChpaterJoinCount(int chpaterJoinCount) {
		this.chpaterJoinCount = chpaterJoinCount;
	}
	public int getSweepCount() {
		return sweepCount;
	}
	public void setSweepCount(int sweepCount) {
		this.sweepCount = sweepCount;
	}
	public int getBuySweepCount() {
		return buySweepCount;
	}
	public void setBuySweepCount(int buySweepCount) {
		this.buySweepCount = buySweepCount;
	}
	public String getTotalLoginAward() {
		return totalLoginAward==null?"":totalLoginAward;
	}
	public void setTotalLoginAward(String totalLoginAward) {
		this.totalLoginAward = totalLoginAward;
	}
	public String getOnTimeLoginAward() {		
		return onTimeLoginAward==null?"":onTimeLoginAward;
	}
	public void setOnTimeLoginAward(String onTimeLoginAward) {
		this.onTimeLoginAward = onTimeLoginAward;
	}
	public int getTotalLogin() {
		return totalLogin;
	}
	public void setTotalLogin(int totalLogin) {
		this.totalLogin = totalLogin;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getFriendlyPoint() {
		return friendlyPoint;
	}
	public void setFriendlyPoint(int friendlyPoint) {
		this.friendlyPoint = friendlyPoint;
	}
	public List<Integer> getUseFriend() {
		return useFriend;
	}
	public void setUseFriend(List<Integer> useFriend) {
		this.useFriend = useFriend;
	}
	public List<Integer> getHandselFriend() {
		return handselFriend;
	}
	public void setHandselFriend(List<Integer> handselFriend) {
		this.handselFriend = handselFriend;
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
	public int getBuyStaminaCount() {
		return buyStaminaCount;
	}
	public void setBuyStaminaCount(int buyStaminaCount) {
		this.buyStaminaCount = buyStaminaCount;
	}
	public int getBuyExploretionCount() {
		return buyExploretionCount;
	}
	public void setBuyExploretionCount(int buyExploretionCount) {
		this.buyExploretionCount = buyExploretionCount;
	}
	public String getContinuousLoginGiftMark() {
		return continuousLoginGiftMark;
	}
	public void setContinuousLoginGiftMark(String continuousLoginGiftMark) {
		this.continuousLoginGiftMark = continuousLoginGiftMark;
	}
	public String getLevelGiftMark() {
		return levelGiftMark;
	}
	public void setLevelGiftMark(String levelGiftMark) {
		this.levelGiftMark = levelGiftMark;
	}
	public String getProductDayMark() {
		return productDayMark;
	}
	public void setProductDayMark(String productDayMark) {
		this.productDayMark = productDayMark;
	}
	public int getActivityRechargeCurrency() {
		return activityRechargeCurrency;
	}
	public void setActivityRechargeCurrency(int activityRechargeCurrency) {
		this.activityRechargeCurrency = activityRechargeCurrency;
	}
	public int getCoinHandNum() {
		return coinHandNum;
	}
	public void setCoinHandNum(int coinHandNum) {
		this.coinHandNum = coinHandNum;
	}
	/*public int getResurrectionNum() {
		return resurrectionNum;
	}
	public void setResurrectionNum(int resurrectionNum) {
		this.resurrectionNum = resurrectionNum;
	}*/
	public int getCallSoulFriendPointNum() {
		return callSoulFriendPointNum;
	}
	public void setCallSoulFriendPointNum(int callSoulFriendPointNum) {
		this.callSoulFriendPointNum = callSoulFriendPointNum;
	}
	public int getCallSoulCurrencyNum() {
		return callSoulCurrencyNum;
	}
	public void setCallSoulCurrencyNum(int callSoulCurrencyNum) {
		this.callSoulCurrencyNum = callSoulCurrencyNum;
	}
	public int getDayTotalCurrency() {
		return dayTotalCurrency;
	}
	public void setDayTotalCurrency(int dayTotalCurrency) {
		this.dayTotalCurrency = dayTotalCurrency;
	}
	public int getActivityContinuousRechargeCount() {
		return activityContinuousRechargeCount;
	}
	public void setActivityContinuousRechargeCount(int activityContinuousRechargeCount) {
		this.activityContinuousRechargeCount = activityContinuousRechargeCount;
	}
	public Date getLastRechargeTime() {
		return lastRechargeTime;
	}
	public void setLastRechargeTime(Date lastRechargeTime) {
		this.lastRechargeTime = lastRechargeTime;
	}
	public int getActivityTotalCostCurrency() {
		return activityTotalCostCurrency;
	}
	public void setActivityTotalCostCurrency(int activityTotalCostCurrency) {
		this.activityTotalCostCurrency = activityTotalCostCurrency;
	}
	public int getOnlineGiftGetCount() {
		return onlineGiftGetCount;
	}
	public void setOnlineGiftGetCount(int onlineGiftGetCount) {
		this.onlineGiftGetCount = onlineGiftGetCount;
	}
	public int getActivityContinuousRechargeGetMark() {
		return activityContinuousRechargeGetMark;
	}
	public void setActivityContinuousRechargeGetMark(int activityContinuousRechargeGetMark) {
		this.activityContinuousRechargeGetMark = activityContinuousRechargeGetMark;
	}
	public Date getFreeCallSoulTime() {
		return freeCallSoulTime;
	}
	public void setFreeCallSoulTime(Date freeCallSoulTime) {
		this.freeCallSoulTime = freeCallSoulTime;
	}
	public Date getMysteryShopFreshTime() {
		return mysteryShopFreshTime;
	}
	public void setMysteryShopFreshTime(Date mysteryShopFreshTime) {
		this.mysteryShopFreshTime = mysteryShopFreshTime;
	}
}
