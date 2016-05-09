package com.ks.action.logic;

import java.util.List;

import com.ks.protocol.vo.goods.EqRefiningVO;
import com.ks.protocol.vo.goods.EqRepairRetVO;
import com.ks.protocol.vo.goods.FightPropVO;
import com.ks.protocol.vo.goods.GainAwardVO;
import com.ks.protocol.vo.goods.SellGoodsResultVO;
import com.ks.protocol.vo.goods.SellGoodsVO;
import com.ks.protocol.vo.goods.UserBakPropVO;
import com.ks.protocol.vo.goods.UserGoodsVO;

/**
 * 用户物品
 * @author ks
 */
public interface UserGoodsAction {
	
	/**
	 * 使用装备
	 * @param userId 用户编号
	 * @param userSoulId 战魂编号
	 * @param gridId 格子编号
	 */
	void useEquipment(int userId,long userSoulId,int gridId);
	
	/**
	 * 取消使用装备
	 * @param userId 用户编号
	 * @param gridId 格子编号
	 */
	void unUseEquipment(int userId,int gridId);
	
	/**
	 * 使用道具
	 * @param userId 用户编号
	 * @param gridId 格子编号
	 */
	void useProp(int userId,int gridId);
	
	/**
	 * 修改战斗道具
	 * @param userId 用户编号
	 * @param fps 要修改的战斗道具
	 * @return 修改后的格子
	 */
	List<UserGoodsVO> updateFightProp(int userId,List<FightPropVO> fps);
	
	/**
	 * 卖出道具
	 * @param userId 用户编号
	 * @param sellGoods 卖出道具
	 * @return 结果
	 */
	SellGoodsResultVO sellGoods(int userId,List<SellGoodsVO> sellGoods);
	/**
	 * 增加道具仓库容量
	 * @param userId 用户编号
	 * @return 道具仓库容量
	 */
	int addItemCapacity(int userId);
	/**
	 * 合成物品
	 * @param userId 用户编号
	 * @param id 合成编号
	 * @return 合成结果
	 */
	GainAwardVO synthesisGoods(int userId,int id);
	/**
	 * 修理装备
	 * @param userId
	 * @param gridId
	 * @return
	 */
	EqRepairRetVO repairEquipment(int userId,int gridId);
	
	/**
	 * 用户副本道具
	 * @param userId
	 * @return
	 */
	List<UserBakPropVO> gainUserBakProps(int userId);
	
	/***
	 * 精炼
	 * @param userId
	 * @param eqGridId
	 * @param propId
	 * @return
	 */
	public EqRefiningVO eqRefining(int userId, int eqGridId,int propGridId);
}
