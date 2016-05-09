package com.ks.logic.service;

import java.util.List;

import com.ks.access.Transaction;
import com.ks.model.goods.Backage;
import com.ks.model.goods.FightProp;
import com.ks.model.goods.Goods;
import com.ks.model.goods.UserBakProp;
import com.ks.model.goods.UserGoods;
import com.ks.model.user.User;
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
public interface UserGoodsService {
	/**
	 * 获得背包
	 * @param userId 用户编号
	 * @return 用户背包
	 */
	Backage getPackage(int userId);
	/**
	 * 初始化用户物品
	 * @param userId 用户编号
	 * @return 用户物品
	 */
	@Transaction
	List<UserGoods> initUserGoods(int userId);
	/**
	 * 增加物品
	 * @param user 用户
	 * @param backage 背包
	 * @param goodses 物品编号
	 * @param type 操作类型
	 * @param description 描述
	 * @return 修改过的用户物品
	 */
	List<UserGoodsVO> addGoods(User user,Backage backage,List<Goods> goodses,int type,String description);
	
	/**
	 * 使用装备
	 * @param userId 用户编号
	 * @param userSoulId 战魂编号
	 * @param gridId 格子编号
	 */
	@Transaction
	void useEquipment(int userId,long userSoulId,int gridId);
	
	/**
	 * 取消使用装备
	 * @param userId 用户编号
	 * @param gridId 格子编号
	 */
	void unUseEquipment(int userId,int gridId);
	/**
	 * 初始化战斗道具
	 * @param userId 用户编号
	 */
	@Transaction
	List<FightProp> initFightProp(int userId);
	/**
	 * 用户下线清除背包
	 * @param userId 用户编号
	 */
	@Transaction
	void clearPackage(int userId);
	/**
	 * 用户下线清除战斗道具
	 * @param userId 用户编号
	 */
	@Transaction
	void clearFightProps(int userId);
	/**
	 * 修改战斗道具
	 * @param userId 用户编号
	 * @param fps 要修改的战斗道具
	 * @return 修改后的格子
	 */
	@Transaction	
	List<UserGoodsVO> updateFightProp(int userId,List<FightPropVO> fps);
	
	/**
	 * 删除物品
	 * @param backage 背包
	 * @param goodsType 物品类型
	 * @param goodsId 物品编号
	 * @param num 删除数量
	 * @param type 操作类型
	 * @param description 描述
	 * @return 所影响的格子
	 */
	List<UserGoods> deleteGoods(Backage backage,int goodsType,int goodsId,int num,int type,String description);
	/**
	 * 使用道具
	 * @param userId 用户编号
	 * @param gridId 格子编号
	 */
	void useProp(int userId,int gridId);
	
	/**
	 * 卖出道具
	 * @param userId 用户编号
	 * @param sellGoods 卖出道具
	 * @return 结果
	 */
	@Transaction
	SellGoodsResultVO sellGoods(int userId,List<SellGoodsVO> sellGoods);
	/**
	 * 验证背包时候满了
	 * @param backage 背包
	 * @param user 用户
	 */
	void checkBackageFull(Backage backage,User user);
	
	/**
	 * 增加道具仓库容量
	 * @param userId 用户编号
	 * @return 剩余金钱
	 */
	@Transaction
	int addItemCapacity(int userId);
	
	/**
	 * 获取玩家所有物品
	 * @param userId 用户编号
	 * @return 所有物品
	 */
	List<UserGoods> gainUserGoods(int userId);
	/**
	 * 合成物品
	 * @param userId 用户编号
	 * @param id 合成编号
	 * @return 合成结果
	 */
	@Transaction
	GainAwardVO synthesisGoods(int userId,int id);
	/**
	 * 修理装备
	 * @param userId
	 * @param gridId
	 */
	@Transaction
	EqRepairRetVO repairEuipment(int userId,int gridId);
	/**
	 * 用户副本物品
	 * @param userId
	 * @return
	 */
	List<UserBakPropVO> gainUserBakProps(int userId);
	/**
	 * 添加用户副本物品
	 * @param userId
	 * @return
	 */
	@Transaction
	UserBakProp addBakProps(User user,int propId, int num, int logType,String des);
	
	/**
	 * 添加用户副本物品
	 * @param userId
	 * @return
	 */
	@Transaction
	void useBakProps(int userId,int propId, int num, int logType,String des);
	/**
	 * 用户下线清除战斗道具
	 * @param userId 用户编号
	 */
	@Transaction
	void clearUserBakProps(int userId);
	
	public List<UserBakProp> initUseBakProp(int userId);
	
	/**
	 * 准备精炼
	 * @return
	 */
	@Transaction
	public EqRefiningVO eqRefining(int userId,int equipmentGridId,int propGridId);
}
