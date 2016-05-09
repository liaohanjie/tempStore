package com.ks.logic.service;

import java.util.List;

import com.ks.access.Transaction;
import com.ks.model.activity.ActivityDefine;
import com.ks.model.activity.ActivityGift;
import com.ks.model.activity.ActivityPrice;
import com.ks.model.activity.BuyCoinGift;
import com.ks.model.activity.FlashGiftBag;
import com.ks.model.activity.OnTimeLoginGift;
import com.ks.model.activity.TotalLoginGift;
import com.ks.model.dungeon.DropRateMultiple;
import com.ks.model.user.User;
import com.ks.model.user.UserStat;
import com.ks.protocol.vo.activity.ActivityDefineVO;
import com.ks.protocol.vo.activity.ActivityVO;
import com.ks.protocol.vo.activity.CallSoulNoticeVO;
import com.ks.protocol.vo.activity.ChargeGiftVO;
import com.ks.protocol.vo.activity.FlashGiftBagVO;
import com.ks.protocol.vo.activity.PointPriceVO;
import com.ks.protocol.vo.dungeon.DropRateMultipleVO;
import com.ks.protocol.vo.mission.UserAwardVO;

/**
 * 
 * @author living.li
 * @date 2014年4月9日
 */
public interface ActivityService {
	/**
	 * 发送登录奖励
	 * 
	 * @param user
	 * @param stat
	 */
	@Transaction
	public void getLoginGift(User user, UserStat stat);

	public List<OnTimeLoginGift> queryOnTimeGift();

	public List<TotalLoginGift> queryTotalLoginGift();

	/**
	 * 活动是在进行中
	 * 
	 * @param id
	 * @return
	 */
	public boolean activityIsStart(int defineId);
	/**
	 * 活动是在进行中
	 * @param define
	 * @return
	 */
	boolean activityIsStart(ActivityDefine define);

	/**
	 * 检查活动
	 * 
	 * @param id
	 */
	public void checkActivity(int defineId);

	/**
	 * 
	 * 进行中活动
	 * 
	 * @return
	 */
	public List<ActivityDefine> getStartingAc();

	/**
	 * 进行中活动
	 * 
	 * @return
	 */
	public List<ActivityDefineVO> getStartingAcVo();

	/**
	 * 修改活动
	 * 
	 * @param de
	 */
	@Transaction
	public void updateAcCache(ActivityDefine de);

	/**
	 * 获取活动
	 * 
	 * @param id
	 * @return
	 */
	public ActivityDefine getActivityById(int id);
	
	
	/**
	 * 获取活动信息
	 * @param defineId
	 * @return
	 */
	List<ActivityDefine> getActivityByDefineId(int defineId);

	/**
	 * boss后台查询全部活动
	 */
	public List<ActivityDefine> bossGetAllAc();
	
	/**
	 * boss后台修改活动
	 */
	@Transaction
	public void updateActivity(ActivityDefine d);

	/**
	 * 启动服务器重新加载cache
	 * 
	 * @param id
	 * @return
	 */
	public void initActivity();

	/**
	 * 获得充值活动信息
	 * 
	 * @param userId
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
	 * 启动服务器重新加载cache
	 * 
	 * @param id
	 * @return
	 */
	public void initDropRateMultiple();
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
	@Transaction
	public void updateDropRateMultiple(DropRateMultiple drm);

	/**
	 * 添加概率倍数
	 * 
	 * @param drm
	 */
	@Transaction
	public void addDropRateMultiple(DropRateMultiple drm);

	/**
	 * 删除
	 * 
	 * @param drm
	 */
	@Transaction
	public void deleteDropRateMultiple(int id);

	/**
	 * 显示指定召唤出的战魂soulId
	 * 
	 * @return
	 */
	public CallSoulNoticeVO getActivitySoulId();

	/**
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
	 * 修改限时礼包活动
	 * @param ad
	 * @param gift
	 * @param clearData
	 */
	@Transaction
	public void updateBuyConGift(ActivityDefine ad, BuyCoinGift gift,boolean clearData);
	
	/**
	 * boss 限量礼包信息
	 * @return
	 */
	public FlashGiftBag getFlashGiftBagInfo();
	
	/**
	 * 修改限时礼包活动
	 * @param ad
	 * @param gift
	 * @param clearData
	 */
	@Transaction
	public void updateFlashGiftBag(ActivityDefine ad, FlashGiftBag gift);
	

	/**
	 * 活动概率配置集合
	 * 
	 * @return
	 */
	public List<DropRateMultipleVO> queryDropRateMultipleListBySite();

	/**
	 * 查看活动打折价格
	 * 
	 * @return
	 */
	public ActivityPrice getActivityPrices();

	/**
	 * 修改活动打折价格
	 * 
	 * @param price
	 */
	@Transaction
	public void updateActivityPrice(ActivityPrice price);
	
	/**
	 * 查询所有活动礼包信息[一个活动对应多个礼包]
	 * 
	 * @return
	 */
	public List<ActivityGift> queryAllActivityGift();
	
	/**
	 * 查询指定活动礼包
	 * @param defineId
	 * @return
	 */
	public List<ActivityGift> queryAcitivityGift(int defineId);
	
	/**
	 * 查询活动礼包信息[一个活动对应多个礼包]
	 * 
	 * @param userId
	 * @param defineId
	 * @return
	 */
	@Transaction
	public ActivityVO queryActivityGift(int userId, int defineId);
	
	/**
	 * 领取活动礼包，并发放
	 * 
	 * @param userId
	 * @param activityGiftId
	 * @param defineId
	 * @return
	 */
	@Transaction
	public UserAwardVO getActivityGift(int userId, int activityGiftId, int defineId);
	
	/**
	 * 发放活动礼包内容
	 * @param user
	 * @param gift
	 * @return
	 */
	@Transaction
	public UserAwardVO issueGift(User user, ActivityGift gift);

	/**
	 * 查询活动列表礼包信息[一个活动对应多个礼包]
	 * @param userId
	 * @param defineIds
	 * @return
	 */
	@Transaction
	List<ActivityVO> queryActivityGift(int userId, List<Integer> defineIds);

	/**
	 * 冲榜活动奖励发放
	 */
	@Transaction
	void issueActivityRankAward();

	/**
	 * 获取当前有效的活动定义活动信息
	 * @param defineId
	 * @return
	 */
	ActivityDefine getCurrentActivityByDefineId(int defineId);

	/**
	 * 清空指定活动记录标记或数据
	 * @param defineId
	 * 活动定义编号
	 */
	@Transaction
	void cleanActivityRecordData(int defineId);
	
}