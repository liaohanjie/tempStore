package com.ks.logic.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.ks.exceptions.GameException;
import com.ks.logger.LoggerFactory;
import com.ks.logic.dao.opt.SQLOpt;
import com.ks.logic.dao.opt.UserStatOpt;
import com.ks.logic.service.BaseService;
import com.ks.logic.service.MysteryShopItemService;
import com.ks.logic.utils.DateUtils;
import com.ks.model.game.Stat;
import com.ks.model.goods.Backage;
import com.ks.model.goods.Goods;
import com.ks.model.logger.LoggerType;
import com.ks.model.shop.MysteryItemRecord;
import com.ks.model.shop.MysteryShopItem;
import com.ks.model.user.User;
import com.ks.model.user.UserStat;
import com.ks.protocol.MessageFactory;
import com.ks.protocol.vo.mission.UserAwardVO;
import com.ks.protocol.vo.shop.MysteryShopItemVO;
import com.ks.util.RandomUtil;

/**
 * 神秘商店 Service
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2016年1月15日
 */
public class MysteryShopItemServiceImpl extends BaseService implements MysteryShopItemService {

	private Logger logger = LoggerFactory.get(MysteryShopItemServiceImpl.class);
	
	/**需要显示神秘商店购买类型*/
	private int[] typeIds = {1, 2, 3, 4};
	
	@Override
    public List<MysteryShopItem> queryAll() {
	    return mysteryShopItemDAO.queryAll();
    }

	@Override
    public List<MysteryShopItemVO> queryByUserId(int userId) {
		UserStat userStat = userStatDAO.queryUserStat(userId);
		Date freshTime = userStat.getMysteryShopFreshTime();
		
		List<MysteryShopItem> list = null;
		boolean isPrivate = false;
		
		sysFresh(userId);
		
		if (freshTime != null) {
			// 用户刷新时间是否在有效期内
			if (userStat.getMysteryShopFreshTime().getTime() > System.currentTimeMillis()) {
				isPrivate = true;
			}
		}
		
		if (isPrivate) {
			list = mysteryShopItemDAO.getCache(userId);
		} else {
			list = mysteryShopItemDAO.getCache();
		}
		
		//以免清空出错
		if (list == null || list.isEmpty()) {
			list = _randomItem();
			mysteryShopItemDAO.saveCache(list);
		}
	    return createMysteryShopItemVOList(list);
    }

	@Override
    public List<MysteryShopItemVO> fresh(int userId, int typeId) {
		User user = userService.getExistUser(userId);
		
		// 扣除神秘商店刷新卷或魂钻
		try {
			Backage backage = userGoodsService.getPackage(userId);
			userGoodsService.deleteGoods(backage, Goods.TYPE_PROP, 3050005, 1, LoggerType.TYPE_神秘商店, "");
		} catch(Exception e) {
			userService.decrementCurrency(user, 20, LoggerType.TYPE_刷新神秘商店, "mysteryshop");
		}
		
		// 判断是否在刷新时间内，如果是刷新时间内，取私有缓存，否则取公共缓存
		Stat stat = statDAO.findById(Stat.ID_MYSTERY_SHOP_UPDATE_TIME);
		long now = System.currentTimeMillis();
		
		// 取公共缓存更新
		boolean reloadPrivate = false;
		UserStat userStat = userStatDAO.queryUserStat(userId);
		if (userStat.getMysteryShopFreshTime() == null || now > stat.getValue()) {
//			reload = true;
		} else {
			if (now < userStat.getMysteryShopFreshTime().getTime()) {
				reloadPrivate = true;
			}
		}
		
		// 刷新相应类型物品，并保存
		List<MysteryShopItem> list = new ArrayList<>();
		if (reloadPrivate) {
			List<MysteryShopItem> listUserItems = mysteryShopItemDAO.getCache(userId);
			if (listUserItems == null || listUserItems.isEmpty()) {
				listUserItems = mysteryShopItemDAO.getCache();
			}
			
			for (MysteryShopItem item : listUserItems) {
				if (item.getType() != typeId) {
					list.add(item);
				}
			}
		}
		
		if (list.isEmpty()) {
			for(MysteryShopItem item : mysteryShopItemDAO.getCache()) {
				if (item.getType() != typeId) {
					list.add(item);
				}
			}
		}
		
		// 刷新指定类型
		list.addAll(_randomItem(typeId));
		mysteryShopItemDAO.saveCache(userId, list);
		
		// 删除今天购买记录
		Date startTime = DateUtils.getCurrentDate(0, 0, 0).getTime();
		Date endTime = DateUtils.getCurrentDate(24, 0, 0).getTime();
		mysteryItemRecordDAO.delete(userId, typeId, startTime, endTime);
		
		// 记录下一次刷新时间
		userStat.setMysteryShopFreshTime(DateUtils.getCurrentDate(24, 0, 0).getTime());
		UserStatOpt opt = new UserStatOpt();
		opt.mysteryShopFreshTime = SQLOpt.EQUAL;
		userStatDAO.updateUserStat(opt, userStat);
		
	    return createMysteryShopItemVOList(list);
    }
	
	/**
	 * 随机生成所有神秘商店物品
	 * @return
	 */
	private List<MysteryShopItem> _randomItem() {
		List<MysteryShopItem> list = new ArrayList<>();
		
		for (int id : typeIds) {
			list.addAll(_randomItem(id));
		}
		return list;
	}
	
	/**
	 * 随机查找一个类型的 12 个商品
	 * @param typeId
	 * @return
	 */
	private List<MysteryShopItem> _randomItem(int type) {
		List<MysteryShopItem> list = mysteryShopItemDAO.queryByType(type);
		Set<Integer> existedSet = new HashSet<>();
		
		if (list == null || list.size() < 12) {
			throw new GameException(GameException.CODE_参数错误, " mysteryitem must be more than 12, type=" + type + ", size=" + list.size()); 
		}
		
		// 计算权重值
		int totalWeight = 0;
		for (MysteryShopItem entity : list){
			totalWeight = totalWeight + entity.getWeight();
		}
		for (MysteryShopItem entity : list){
			entity.setTotalWeight(totalWeight);
		}
		
		List<MysteryShopItem> resList = new ArrayList<>();
		while(true) {
			MysteryShopItem item = (MysteryShopItem) RandomUtil.getRandom(list);
			if (!existedSet.contains(item.getId())) {
				existedSet.add(item.getId());
				resList.add(item);
			}
			
			if (existedSet.size() >= 12) {
				break;
			}
		}
		return resList;
	}
	
	private List<MysteryShopItemVO> createMysteryShopItemVOList(List<MysteryShopItem> list) {
		List<MysteryShopItemVO> listVo = new ArrayList<>();
		for (MysteryShopItem o : list) {
			MysteryShopItemVO vo = MessageFactory.getMessage(MysteryShopItemVO.class);
			vo.init(o);
			
			listVo.add(vo);
		}
		return listVo;
	}

	/**
	 * 系统刷新神秘商店表
	 * 12点24点刷新
	 */
    public void sysFresh(int userId) {
		try {
			Calendar nowCalendar = Calendar.getInstance();
			
			Stat stat = statDAO.findById(Stat.ID_MYSTERY_SHOP_UPDATE_TIME);
			if (stat == null) {
				stat = new Stat();
				stat.setId(Stat.ID_MYSTERY_SHOP_UPDATE_TIME);
				stat.setValue(0);
			}
			
			// 判断时间是否应该刷新, 记录下一次应该刷新的时间
			if (stat.getValue() == 0 || nowCalendar.getTime().getTime() > stat.getValue()) {
				mysteryShopItemDAO.saveCache(_randomItem());
				
				Calendar nextFreshTimeCalendar = DateUtils.getCurrentDate(24, 0, 0);
				stat.setValue(nextFreshTimeCalendar.getTime().getTime());
				statDAO.update(stat);
				
				mysteryItemRecordDAO.deleteByUserId(userId);
			}
		} catch(Exception e){
			logger.warn(e.getMessage());
		}
    }

	@Override
    public UserAwardVO buy(int userId, int id) {
		User user = userService.getExistUser(userId);
		
		MysteryShopItem item = mysteryShopItemDAO.findById(id);
		if (item == null) {
			throw new GameException(GameException.CODE_参数错误, "can't find mystershopitem id=" + id);
		}
		
		// 判断是否购买 24点 (神秘商店的刷新时间是24点)
		Date startTime = DateUtils.getCurrentDate(0, 0, 0).getTime();
		Date endTime = DateUtils.getCurrentDate(24, 0, 0).getTime();
		List<MysteryItemRecord> list = mysteryItemRecordDAO.query(userId, item.getId(), startTime, endTime);
		if (list != null && !list.isEmpty()) {
			throw new GameException(GameException.CODE_重复购买, "can't buy twice times between " + startTime + ", endTime=" + endTime + " ,userId=" + userId + " ,itemId=" + item.getId());
		}
		
		// 扣除相应类型金钱
		switch(item.getType()) {
		case 1:
			userService.decrementGold(user, item.getPrice(), LoggerType.TYPE_神秘商店, "");
			break;
		case 2:
			// something to do
			throw new GameException(GameException.CODE_参数错误, "can't implement's price type=" + item.getType());
//			break;
		case 3:
			userService.decrementPoint(user, item.getPrice(), LoggerType.TYPE_神秘商店, "");
			break;
		case 4:
			userService.decrementHonor(user, item.getPrice(), LoggerType.TYPE_神秘商店, "");
			break;
		default:
			logger.warn("mystery buy type error. type=" + item.getType());
			break;
		}
		
		// 记录购买记录
		MysteryItemRecord record = new MysteryItemRecord();
		record.setUserId(userId);
		record.setTypeId(item.getType());
		record.setItemId(item.getId());
		record.setCreateTime(new Date());
		mysteryItemRecordDAO.add(record);
		
		// 发放物品
		List<Goods> goodsList = Arrays.asList(Goods.create(item.getGoodsId(), item.getGoodsType(), item.getNum(), item.getLevel()));
	    return goodsService.getUserAwardVO(goodsList, user, LoggerType.TYPE_商城购买, item.getId() + "");
    }

	@Override
    public List<Integer> queryCurrentAllRecord(int userId) {
		// 神秘商店的刷新时间是24点, 记录一天内有效
		Date startTime = DateUtils.getCurrentDate(0, 0, 0).getTime();
		Date endTime = DateUtils.getCurrentDate(24, 0, 0).getTime();
		List<MysteryItemRecord> list = mysteryItemRecordDAO.queryAll(userId, startTime, endTime);
		
		List<Integer> listItemIds = new ArrayList<>();
		if (list != null) {
			for (MysteryItemRecord record : list) {
				listItemIds.add(record.getItemId());
			}
		}
	    return listItemIds;
    }
}
