package com.ks.action.logic;

import java.util.List;

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

public interface ActivityAction {
	/**
	 * 进行中活动
	 * 
	 * @return
	 */
	public List<ActivityDefineVO> getStartActivity();

	/**
	 * boss后台查询全部活动
	 */
	public List<ActivityDefine> bossGetAllAc();

	/**
	 * boss后台修改活动
	 */
	public void updateActivity(ActivityDefine d);

	/**
	 * 充值活动信息
	 * 
	 * @return
	 */
	public ChargeGiftVO getChargeAcInfo(int userId);

	/**
	 * 限时礼包信息
	 * 
	 * @param userId
	 * @return
	 */
	public FlashGiftBagVO getFlashGiftBag();

	/**
	 * 活动概率配置集合
	 * 
	 * @return
	 */
	public List<DropRateMultiple> queryDropRateMultipleList();

	/**
	 * 修改概率翻倍
	 * 
	 * @param drm
	 */
	public void updateDropRateMultiple(DropRateMultiple drm);

	/**
	 * 添加概率倍数
	 * 
	 * @param drm
	 */
	public void addDropRateMultiple(DropRateMultiple drm);

	/**
	 * 删除
	 * 
	 * @param drm
	 */
	public void deleteDropRateMultiple(int id);

	/**
	 * 显示指定召唤出的战魂soulId
	 * 
	 * @return
	 */
	public CallSoulNoticeVO getActivitySoulId();

	/**
	 * 活动各种价格信息
	 * 
	 * @return
	 */
	public PointPriceVO getPointPrice();

	/**
	 * boss 限时礼包信息
	 * 
	 * @return
	 */
	public BuyCoinGift getBuyCoinGift();

	/**
	 * 修改活动打折价格
	 * 
	 * @param price
	 */
	public void updateActivityPrice(ActivityPrice price);

	/***
	 * 查看活动打折价格
	 * 
	 * @return
	 */
	public ActivityPrice getActivityPrices();

	/**
	 * 修改限时礼包活动
	 * 
	 * @param ad
	 * @param gift
	 * @param clearData
	 */
	public void updateBuyConGift(ActivityDefine ad, BuyCoinGift gift, boolean clearData);

	FlashGiftBag getFlashGiftBagInfo();

	/**
	 * 修改限时礼包活动
	 * 
	 * @param ad
	 * @param gift
	 * @param clearData
	 */
	public void updateFlashGiftBag(ActivityDefine ad, FlashGiftBag gift);

	/**
	 * 活动概率配置集合
	 * 
	 * @return
	 */
	public List<DropRateMultipleVO> queryDropRateMultipleListBySite();

	/**
	 * 查询活动信息，以及活动礼包状态[一个活动对应多个礼包]
	 * 
	 * @param defineId
	 * @return
	 */
	public ActivityVO queryActivity(int userId, int defineId);

	/**
	 * 领取活动礼包
	 * 
	 * @param userId
	 * @param activityGiftId
	 * @param defindeId
	 * @return
	 */
	public UserAwardVO getActivityGift(int userId, int activityGiftId, int defindeId);

	/**
	 * 查询多个活动信息，以及活动礼包[一个活动对应多个礼包]
	 * 
	 * @param userId
	 * @param defineIds
	 * @return
	 */
	List<ActivityVO> queryActivityList(int userId, List<Integer> defineIds);

	/**
	 * 查询活动定义信息
	 * 
	 * @param defineId
	 * @return
	 */
	ActivityDefine findActivityDefineByDefineId(int defineId);

	/**
	 * 查询指定活动礼包信息
	 * 
	 * @param defineId
	 * @return
	 */
	List<ActivityGift> queryActivityGiftByDefineId(int defineId);

	/**
	 * 冲榜活动奖励发放，等级排行，竞技排行，关卡排行
	 */
	void issueActivityRankAward();

	/**
	 * 清空指定活动记录标记或数据
	 * 
	 * @param defineId
	 */
	void cleanActivityRecordData(int defineId);
}
