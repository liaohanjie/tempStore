package com.ks.protocol.vo.user;

import java.util.Calendar;
import java.util.List;

import com.ks.model.soul.CallRule;
import com.ks.model.user.UserStat;
import com.ks.protocol.Message;
import com.ks.protocol.vo.shop.ProductBuyCountVO;

public class UserStatVO extends Message {

	private static final long serialVersionUID = 1L;
	
	/**友情点*/
	private int friendlyPoint;
	/**使用过的好友*/
	private List<Integer> useFriend;
	/**赠送过礼物的好友*/
	private List<Integer> handselFriend;
	/**购买体力次数*/
	private int buyStaminaCount;	
	/**累计登录*/
	private int totalLogin;
	/**已领取的登录奖励*/
	private String onTimeLoginAward;
	/**已经领取的成长奖励*/
	private String growthGift;
	/**vip已领奖时间*/
	private long vipAwardTime;
	/**vip已扫荡次数*/
	private int sweepCount;	
	/**今日乱入总次数**/
	private int chpaterJoinCount;
	/**vip已购买扫荡次数*/
	private  int buySweepCount;
	/**每天领体力次数*/
	private int sendStamainCount;
	/**立即结束探索次数*/
	private int buyExploretionCount;
	/**有限制购买次数的商品购买记录*/
	private List<ProductBuyCountVO> productBuyCountVoList;
	/**限时活动充值(单位：魂钻)*/
	private int activityRechargeCurrency;
	/**点金手当天已使用次数，0点置0*/
	private int coinHandNum;
	/**当天复活次数*/
	//private int resurrectionNum;
	/**友情点召唤必出五星战魂剩余次数*/
	private int callSoulFriendPointNum;
	/**魂钻召唤必出五星战魂次数*/
	private int callSoulCurrencyNum;
	/**活动内累计消费额*/
	private int activityTotalCostCurrency;
	/**在线礼包领取次数(按天)*/
	private int onlineGiftGetCount;
	/**下一次免费召唤时间*/
	private long nextFreeCallSoulTime;
	/**活动单日累计充值*/
	private int dayTotalCurrency;
	
	public void init(UserStat o){
		this.friendlyPoint = o.getFriendlyPoint();
		this.useFriend = o.getUseFriend();
		this.handselFriend = o.getHandselFriend();
		this.buyStaminaCount = o.getBuyStaminaCount();
		this.totalLogin=o.getTotalLogin();
		this.onTimeLoginAward=o.getOnTimeLoginAward();
		this.growthGift=o.getGrowthGift();
		this.vipAwardTime=o.getVipAwardTime().getTime();
		this.sweepCount=o.getSweepCount();
		this.buySweepCount=o.getBuySweepCount();
		this.chpaterJoinCount=o.getChpaterJoinCount();
		this.buyExploretionCount=o.getBuyExploretionCount();
		this.sendStamainCount=o.getSendStamainCount();
		this.activityRechargeCurrency = o.getActivityRechargeCurrency();
		this.coinHandNum = o.getCoinHandNum();
		this.dayTotalCurrency = o.getDayTotalCurrency();
		//this.resurrectionNum = o.getResurrectionNum();
		//this.callSoulCurrencyNum = o.getCallSoulCurrencyNum();
		//this.callSoulFriendPointNum = o.getCallSoulFriendPointNum();
		this.callSoulCurrencyNum = o.getCallSoulCurrencyNum() == 0 ? CallRule.FIRST_FACTOR : (CallRule.FIRST_FACTOR - o.getCallSoulCurrencyNum() % CallRule.FIRST_FACTOR);
		this.callSoulFriendPointNum = o.getCallSoulFriendPointNum() == 0 ? CallRule.FIRST_FACTOR : (CallRule.FIRST_FACTOR - o.getCallSoulFriendPointNum() % CallRule.FIRST_FACTOR);
		this.activityTotalCostCurrency = o.getActivityTotalCostCurrency();
		this.onlineGiftGetCount = o.getOnlineGiftGetCount();
		
		if (o.getFreeCallSoulTime() == null) {
			this.nextFreeCallSoulTime = 0L;
		} else {
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.HOUR_OF_DAY, 5);
			calendar.set(Calendar.MINUTE,0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			
			this.nextFreeCallSoulTime = o.getFreeCallSoulTime().getTime();
			
			if (o.getFreeCallSoulTime().getTime() > calendar.getTime().getTime()) {
				calendar.add(Calendar.DATE, 1);
				this.nextFreeCallSoulTime = calendar.getTime().getTime();
			}
		}
	}
	
	public int getSendStamainCount() {
		return sendStamainCount;
	}
	public void setSendStamainCount(int sendStamainCount) {
		this.sendStamainCount = sendStamainCount;
	}
	public int getBuyExploretionCount() {
		return buyExploretionCount;
	}
	public void setBuyExploretionCount(int buyExploretionCount) {
		this.buyExploretionCount = buyExploretionCount;
	}

	public long getVipAwardTime() {
		return vipAwardTime;
	}
	public void setVipAwardTime(long vipAwardTime) {
		this.vipAwardTime = vipAwardTime;
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

	public int getTotalLogin() {
		return totalLogin;
	}
	public void setTotalLogin(int totalLogin) {
		this.totalLogin = totalLogin;
	}
	public String getOnTimeLoginAward() {
		return onTimeLoginAward;
	}
	public void setOnTimeLoginAward(String onTimeLoginAward) {
		this.onTimeLoginAward = onTimeLoginAward;
	}
	public int getFriendlyPoint() {
		return friendlyPoint;
	}
	
	public String getGrowthGift() {
		return growthGift;
	}
	public void setGrowthGift(String growthGift) {
		this.growthGift = growthGift;
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
	public int getBuyStaminaCount() {
		return buyStaminaCount;
	}
	public void setBuyStaminaCount(int buyStaminaCount) {
		this.buyStaminaCount = buyStaminaCount;
	}
	public int getChpaterJoinCount() {
		return chpaterJoinCount;
	}
	public void setChpaterJoinCount(int chpaterJoinCount) {
		this.chpaterJoinCount = chpaterJoinCount;
	}
	public List<ProductBuyCountVO> getProductBuyCountVoList() {
		return productBuyCountVoList;
	}
	public void setProductBuyCountVoList(List<ProductBuyCountVO> productBuyCountVoList) {
		this.productBuyCountVoList = productBuyCountVoList;
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
	public long getNextFreeCallSoulTime() {
		return nextFreeCallSoulTime;
	}
	public void setNextFreeCallSoulTime(long nextFreeCallSoulTime) {
		this.nextFreeCallSoulTime = nextFreeCallSoulTime;
	}
	public int getDayTotalCurrency() {
		return dayTotalCurrency;
	}
	public void setDayTotalCurrency(int dayTotalCurrency) {
		this.dayTotalCurrency = dayTotalCurrency;
	}
	@Override
    public String toString() {
	    return "UserStatVO [friendlyPoint=" + friendlyPoint + ", useFriend=" + useFriend + ", handselFriend=" + handselFriend + ", buyStaminaCount=" + buyStaminaCount + ", totalLogin=" + totalLogin
	            + ", onTimeLoginAward=" + onTimeLoginAward + ", growthGift=" + growthGift + ", vipAwardTime=" + vipAwardTime + ", sweepCount=" + sweepCount + ", chpaterJoinCount=" + chpaterJoinCount
	            + ", buySweepCount=" + buySweepCount + ", sendStamainCount=" + sendStamainCount + ", buyExploretionCount=" + buyExploretionCount + ", productBuyCountVoList=" + productBuyCountVoList
	            + ", activityRechargeCurrency=" + activityRechargeCurrency + ", coinHandNum=" + coinHandNum + ", callSoulFriendPointNum=" + callSoulFriendPointNum + ", callSoulCurrencyNum="
	            + callSoulCurrencyNum + ", activityTotalCostCurrency=" + activityTotalCostCurrency + ", onlineGiftGetCount=" + onlineGiftGetCount + ", nextFreeCallSoulTime=" + nextFreeCallSoulTime
	            + ", dayTotalCurrency=" + dayTotalCurrency + "]";
    }
}
