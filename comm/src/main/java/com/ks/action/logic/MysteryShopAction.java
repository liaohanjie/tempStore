package com.ks.action.logic;

import java.util.List;

import com.ks.protocol.vo.mission.UserAwardVO;
import com.ks.protocol.vo.shop.MysteryShopItemVO;


/**
 * 神秘商店
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2016年1月15日
 */
public interface MysteryShopAction {
	
	/**
	 * 查询用户神秘商店信息
	 * @param userId
	 * @return
	 */
	List<MysteryShopItemVO> queryAll(int userId);

	/**
	 * 刷新神秘商店
	 * @param userId
	 * @return
	 */
	List<MysteryShopItemVO> fresh(int userId, int typeId);

	/**
	 * 神秘商品购买
	 * @param userId
	 * @param id 神秘商品购买
	 * @return
	 */
	UserAwardVO mysteryShopBuy(int userId, int id);

	/**
	 * 返回当前时段购买记录
	 * @param userId
	 * @return
	 */
	List<Integer> queryCurrentAllRecord(int userId);
}
