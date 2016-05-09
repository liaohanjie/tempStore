package com.ks.wrold.action;

import java.util.List;

import com.ks.action.logic.ActivityAction;
import com.ks.action.world.WorldActivityAction;
import com.ks.app.Application;
import com.ks.model.activity.ActivityDefine;
import com.ks.model.activity.ActivityPrice;
import com.ks.model.activity.BuyCoinGift;
import com.ks.model.activity.FlashGiftBag;
import com.ks.model.dungeon.DropRateMultiple;
import com.ks.protocol.vo.activity.ActivityDefineVO;
import com.ks.rpc.RPCKernel;

public class WorldActivityActionImpl implements WorldActivityAction {

	public static ActivityAction getActivityAction() {
		ActivityAction activityAction = RPCKernel.getRemoteByServerType(Application.LOGIC_SERVER, ActivityAction.class);
		return activityAction;
	}

	@Override
	public List<ActivityDefineVO> getStartActivity() {
		ActivityAction activityAction = getActivityAction();
		return activityAction.getStartActivity();
	}

	@Override
	public List<ActivityDefine> bossGetAllAc() {
		ActivityAction activityAction = getActivityAction();
		return activityAction.bossGetAllAc();
	}

	@Override
	public void updateActivity(ActivityDefine d) {
		ActivityAction activityAction = getActivityAction();
		activityAction.updateActivity(d);
	}

	@Override
	public ActivityPrice getActivityPrices() {
		ActivityAction activityAction = getActivityAction();
		return activityAction.getActivityPrices();
	}

	@Override
	public void updateActivityPrice(ActivityPrice price) {
		ActivityAction activityAction = getActivityAction();
		activityAction.updateActivityPrice(price);
	}

	@Override
	public List<DropRateMultiple> queryDropRateMultipleList() {
		ActivityAction activityAction = getActivityAction();
		return activityAction.queryDropRateMultipleList();
	}

	@Override
	public void updateDropRateMultiple(DropRateMultiple drm) {
		ActivityAction activityAction = getActivityAction();
		activityAction.updateDropRateMultiple(drm);
	}

	@Override
	public void addDropRateMultiple(DropRateMultiple drm) {
		ActivityAction activityAction = getActivityAction();
		activityAction.addDropRateMultiple(drm);

	}

	@Override
	public void deleteDropRateMultiple(int id) {
		ActivityAction activityAction = getActivityAction();
		activityAction.deleteDropRateMultiple(id);
	}

	public BuyCoinGift getBuyCoinGift() {
		return getActivityAction().getBuyCoinGift();
	}

	@Override
	public void updateBuyConGift(ActivityDefine ad, BuyCoinGift gift, boolean clearData) {
		getActivityAction().updateBuyConGift(ad, gift, clearData);
	}

	@Override
	public FlashGiftBag getFlashGiftBagInfo() {
		return getActivityAction().getFlashGiftBagInfo();
	}

	@Override
	public void updateFlashGiftBag(ActivityDefine ad, FlashGiftBag gift) {
		getActivityAction().updateFlashGiftBag(ad, gift);
	}

	@Override
	public void cleanActivityRecordData(int defineId) {
		ActivityAction activityAction = getActivityAction();
		activityAction.cleanActivityRecordData(defineId);
	}

}
