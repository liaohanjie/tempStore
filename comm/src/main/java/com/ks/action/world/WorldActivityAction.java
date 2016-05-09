package com.ks.action.world;

import java.util.List;
import com.ks.model.activity.ActivityDefine;
import com.ks.model.activity.ActivityPrice;
import com.ks.model.activity.BuyCoinGift;
import com.ks.model.activity.FlashGiftBag;
import com.ks.model.dungeon.DropRateMultiple;
import com.ks.protocol.vo.activity.ActivityDefineVO;

public interface WorldActivityAction {
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

	public ActivityPrice getActivityPrices();

	public void updateActivityPrice(ActivityPrice price);

	/**
	 * boss后台修改活动
	 */
	public void updateActivity(ActivityDefine d);

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
	 * boss 限时礼包信息
	 * 
	 * @return
	 */
	public BuyCoinGift getBuyCoinGift();

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
	 * 清空指定活动记录标记或数据
	 * 
	 * @param defineId
	 */
	public void cleanActivityRecordData(int defineId);
}
