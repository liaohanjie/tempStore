package com.ks.game.handler;

import java.util.List;

import com.ks.app.Application;
import com.ks.game.model.Player;
import com.ks.handler.GameHandler;
import com.ks.protocol.MainCmd;
import com.ks.protocol.SubCmd;
import com.ks.protocol.main.MainCMD;
import com.ks.protocol.sub.ActivityCMD;
@MainCmd(mainCmd=MainCMD.MAIN_活动)
public class ActivityHanlder extends ActionAdapter {
	
	@SubCmd(subCmd = ActivityCMD.ACTIVITY_取得活动信息)
	public void gainStartActivity(GameHandler handler) {
		Application.sendMessage(handler.getChannel(), handler.getHead(),
				activityAction().getStartActivity());
	}
	
	
	@SubCmd(subCmd = ActivityCMD.ACTIVITY_我的充值活动信息)
	public void getChargeAcInfo(GameHandler handler) {
		Player player=handler.getPlayer();
		Application.sendMessage(handler.getChannel(), handler.getHead(),
				activityAction().getChargeAcInfo(player.getUserId()));
	}
	
	@SubCmd(subCmd = ActivityCMD.ACTIVITY_限量礼包信息)
	public  void getFlashBagInfo(GameHandler handler) {
		Application.sendMessage(handler.getChannel(), handler.getHead(),
				activityAction().getFlashGiftBag());
	}
	
	@SubCmd(subCmd = ActivityCMD.ACTIVITY_招魂出指定的战魂)
	public  void getActivitySoulId(GameHandler handler) {
		Application.sendMessage(handler.getChannel(), handler.getHead(),
				activityAction().getActivitySoulId());
	}
	@SubCmd(subCmd = ActivityCMD.ACTIVITY_打折价格)
	public  void getActivityPointPrice(GameHandler handler) {
		Application.sendMessage(handler.getChannel(), handler.getHead(),
				activityAction().getPointPrice());
	}
	
	@SubCmd(subCmd = ActivityCMD.ACTIVITY_概率活动翻倍信息)
	public  void getDropRateMultipleListBySite(GameHandler handler) {
		Application.sendMessage(handler.getChannel(), handler.getHead(),
				activityAction().queryDropRateMultipleListBySite());
	}
	
	/**
	 * 活动定义ID，查找活动多个礼包
	 * 
	 * @param handler
	 * @param defineId
	 */
	@SubCmd(subCmd = ActivityCMD.ACTIVITY_GIFT_信息, args = {"int"})
	public void getActivity(GameHandler handler,int defineId) {
		Application.sendMessage(handler.getChannel(), handler.getHead(), activityAction().queryActivity(handler.getPlayer().getUserId(), defineId));
	}
	
	/**
	 * 查询多个活动
	 * @param handler
	 * @param defineIds
	 */
	@SubCmd(subCmd = ActivityCMD.ACTIVITY_GIFT_LIST_信息, args = {"int_true"})
	public void getActivityList(GameHandler handler,List<Integer> defineIds) {
		Application.sendMessage(handler.getChannel(), handler.getHead(), activityAction().queryActivityList(handler.getPlayer().getUserId(), defineIds));
	}
	
	/**
	 * 活动礼包ID，领取活动单个礼包
	 * 
	 * @param handler
	 * @param activityGiftId 活动礼包编号
	 * @param defindeId 活动定义编号
	 */
	@SubCmd(subCmd = ActivityCMD.ACTIVITY_GIFT_领取, args = {"int", "int"})
	public void getPlayerActivityGift(GameHandler handler, int activityGiftId, int defineId) {
		Application.sendMessage(handler.getChannel(), handler.getHead(), activityAction().getActivityGift(handler.getPlayer().getUserId(), activityGiftId, defineId));
	}
}
