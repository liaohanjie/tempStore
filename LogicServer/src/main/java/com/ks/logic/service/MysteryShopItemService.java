package com.ks.logic.service;

import java.util.List;

import com.ks.access.Transaction;
import com.ks.model.shop.MysteryShopItem;
import com.ks.protocol.vo.mission.UserAwardVO;
import com.ks.protocol.vo.shop.MysteryShopItemVO;

/**
 * 神秘商店 Service
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2016年1月15日
 */
public interface MysteryShopItemService {
	
	/**
	 * 查找所有
	 * @param classId
	 * @return
	 */
	List<MysteryShopItem> queryAll();

	/**
	 * 查询用户神秘商店商品信息
	 * @param userId
	 * @return
	 */
	@Transaction
	List<MysteryShopItemVO> queryByUserId(int userId);
	
	/**
	 * 玩家刷新神秘商店，并返回新的商品
	 * @param userId
	 * @param typeId
	 * @return
	 */
	@Transaction
	List<MysteryShopItemVO> fresh(int userId, int typeId);

	/**
	 * 神秘商品购买
	 * @param userId
	 * @param id 商品编号
	 * @return
	 */
	@Transaction
	UserAwardVO buy(int userId, int id);

	/**
	 * 返回当前时段购买记录
	 * @param userId
	 * @return
	 */
	List<Integer> queryCurrentAllRecord(int userId);
	
//	/**
//	 * 系统刷新神秘商店
//	 */
//	@Transaction
//	void sysFresh();
}