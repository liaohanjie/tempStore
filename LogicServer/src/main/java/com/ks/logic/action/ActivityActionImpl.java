package com.ks.logic.action;

import java.util.List;

import com.ks.action.logic.ActivityAction;
import com.ks.logic.service.ActivityService;
import com.ks.logic.service.ServiceFactory;
import com.ks.model.activity.ActivityDefine;
import com.ks.model.activity.ActivityGift;
import com.ks.model.activity.ActivityPrice;
import com.ks.model.activity.BuyCoinGift;
import com.ks.model.activity.FlashGiftBag;
import com.ks.model.dungeon.DropRateMultiple;
import com.ks.protocol.vo.activity.ActivityDefineVO;
import com.ks.protocol.vo.activity.ActivityVO;
import com.ks.protocol.vo.activity.CallSoulNoticeVO;
import com.ks.protocol.vo.activity.ChargeGiftVO;
import com.ks.protocol.vo.activity.FlashGiftBagVO;
import com.ks.protocol.vo.activity.PointPriceVO;
import com.ks.protocol.vo.dungeon.DropRateMultipleVO;
import com.ks.protocol.vo.mission.UserAwardVO;

public  class ActivityActionImpl implements ActivityAction{
	private static ActivityService activityService = ServiceFactory.getService(ActivityService.class);
	@Override
	public List<ActivityDefineVO> getStartActivity() {
		return activityService.getStartingAcVo();
	}
	@Override
	public List<ActivityDefine> bossGetAllAc() {
		return activityService.bossGetAllAc();
	}
	@Override
	public void updateActivity(ActivityDefine d) {
		activityService.updateActivity(d);
		
	}
	@Override
	public ChargeGiftVO getChargeAcInfo(int userId) {
		return activityService.getChargeAcInfo(userId);
	}
	@Override
	public FlashGiftBagVO getFlashGiftBag() {
		return activityService.getFlashGiftBag();
	}
	@Override
	public List<DropRateMultiple> queryDropRateMultipleList() {
		return activityService.queryDropRateMultipleList();
	}
	@Override
	public void updateDropRateMultiple(DropRateMultiple drm) {
		activityService.updateDropRateMultiple(drm);
	}
	@Override
	public void addDropRateMultiple(DropRateMultiple drm) {
		activityService.addDropRateMultiple(drm);
	}
	@Override
	public void deleteDropRateMultiple(int id) {
		activityService.deleteDropRateMultiple(id);
	}
	@Override
	public CallSoulNoticeVO getActivitySoulId() {
		return activityService.getActivitySoulId();
	}
	@Override
	public PointPriceVO getPointPrice() {
		return activityService.getPointPrice();
	}
	@Override
	public BuyCoinGift getBuyCoinGift() {
		return activityService.getBuyCoinGift();
	}
	@Override
	public void updateBuyConGift(ActivityDefine ad, BuyCoinGift gift,
			boolean clearData) {
		activityService.updateBuyConGift(ad, gift, clearData);
	}
	@Override
	public FlashGiftBag getFlashGiftBagInfo() {
		return activityService.getFlashGiftBagInfo();
	}
	@Override
	public void updateFlashGiftBag(ActivityDefine ad, FlashGiftBag gift) {
		activityService.updateFlashGiftBag(ad, gift);
	}
	@Override
	public List<DropRateMultipleVO> queryDropRateMultipleListBySite() {
		return activityService.queryDropRateMultipleListBySite();
	}

	@Override
	public ActivityPrice getActivityPrices() {
		return activityService.getActivityPrices();
		
	}
	@Override
	public void updateActivityPrice(ActivityPrice price) {
		activityService.updateActivityPrice(price);
	}
	@Override
    public ActivityVO queryActivity(int userId, int defineId) {
	    return activityService.queryActivityGift(userId, defineId);
    }
	
	@Override
    public UserAwardVO getActivityGift(int userId, int activityGiftId, int defindeId) {
		return activityService.getActivityGift(userId, activityGiftId, defindeId);
    }
	
	@Override
    public List<ActivityVO> queryActivityList(int userId, List<Integer> defineIds) {
		return activityService.queryActivityGift(userId, defineIds);
    }
	
	@Override
	public List<ActivityGift> queryActivityGiftByDefineId(int defineId){
		return activityService.queryAcitivityGift(defineId);
	}
	
	@Override
	public ActivityDefine findActivityDefineByDefineId(int defineId){
		return activityService.getCurrentActivityByDefineId(defineId);
	}
	
	@Override
    public void issueActivityRankAward() {
		activityService.issueActivityRankAward();
    }
	
	@Override
	public void cleanActivityRecordData(int defineId) {
		activityService.cleanActivityRecordData(defineId);
	}
}
