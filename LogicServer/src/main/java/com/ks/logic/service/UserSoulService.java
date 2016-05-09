package com.ks.logic.service;

import java.util.Collection;
import java.util.List;

import com.ks.access.Transaction;
import com.ks.model.goods.UserGoods;
import com.ks.model.user.User;
import com.ks.model.user.UserSoul;
import com.ks.model.user.UserSoulMap;
import com.ks.protocol.vo.soul.CallSoulResultVO;
import com.ks.protocol.vo.soul.UserSoulInfoVO;
import com.ks.protocol.vo.soul.UserSoulMapVO;
import com.ks.protocol.vo.soul.UserSoulOptResultVO;
import com.ks.protocol.vo.user.UserSoulVO;

/**
 * 用户战魂服务
 * @author ks
 */
public interface UserSoulService {
	/**
	 * 添加用户战魂
	 * @param user 要添加战魂的用户
	 * @param soulId 战魂编号
	 * @param level 等级
	 * @return 增加后的战魂
	 */
	@Transaction
	UserSoul addUserSoul(User user,int soulId,int level,int type);
	
	/**
	 * 初始化用户战魂
	 * @param userId 用户编号
	 * @return 用户所有战魂
	 */
	@Transaction
	List<UserSoul> initUserSoul(int userId);
	/**
	 * 清除用户战魂
	 * @param userId 用户编号
	 */
	@Transaction
	void clearUserSoul(int userId);
	
	/**
	 * 强化战魂
	 * @param userSoulId 要强化的战魂
	 * @param soulIds 被强化的战魂
	 * @return 强化结果
	 */
	@Transaction
	UserSoulOptResultVO strengUserSoul(int userId,long userSoulId,List<Long> soulIds);
	
	/**
	 * 查询在线玩家的用户战魂
	 * @param userId 用户编号
	 * @param userSoulId 战魂编号
	 * @return 在线玩家的战魂
	 */
	UserSoul getExistUserSoulCache(int userId,long userSoulId);
	/**
	 * 查询用户战魂
	 * @param userId 用户编号
	 * @param userSoulId 用户战魂编号
	 * @return 用户战魂
	 */
	UserSoul getExistUserSoul(int userId,long userSoulId);
	
	/**
	 * 删除用户战魂
	 * @param user 用户
	 * @param userSoul 战魂
	 * @param type 操作类型
	 * @param description 描述
	 */
	@Transaction
	void deleteUserSoul(User user,UserSoul userSoul,int type,String description);
	/**
	 * 增加经验
	 * @param user 用户
	 * @param userSoul 战魂
	 * @param num 增加的数量
	 * @param type 操作类型
	 * @param description 描述
	 */
	@Transaction
	void addSoulExp(User user,UserSoul userSoul,int num,int type,String description);
	/**
	 * 战魂进化
	 * @param userId 用户编号
	 * @param userSoulId 要进化战魂的编号
	 * @param soulIds 进化战魂的素材
	 * @return 操作结果
	 */
	@Transaction
	UserSoulOptResultVO soulEvolution(int userId,long userSoulId,List<Long> soulIds);
	
	/**
	 * 卖出战魂
	 * @param userId 用户编号
	 * @param userSoulIds 卖出的战魂
	 * @return 卖出多少钱
	 */
	@Transaction
	int sellSoul(int userId,List<Long> userSoulIds);
	
	/**
	 * 召唤战魂
	 * @param userId 用户编号
	 * @param type 召唤方式
	 * @return 召唤的战魂
	 */
	@Transaction
	CallSoulResultVO callSoul(int userId,int type,int num);
	/**
	 * 获得用户队长战魂信息
	 * @param userId 用户编号
	 * @return 战魂信息
	 */
	UserSoulInfoVO gainUserSoulCapInfo(int userId);
	/**
	 * 获得用户战魂信息
	 * @param soul 用户战魂
	 * @param userGoods 使用中的格子
	 * @return 战魂信息
	 */
	UserSoulInfoVO gainUserSoulInfo(UserSoul soul,Collection<UserGoods> userGoods);
	/**
	 * 验证战魂时候满了
	 * @param user 用户
	 */
	void checkSoulFull(User user);
	/**
	 * 增加战魂大厅容量
	 * @param userId 用户编号
	 * @return 剩余金钱
	 */
	@Transaction
	int addSoulCapacity(int userId);
	/**
	 * 点亮用户战魂
	 * @param userId
	 * @param soulId
	 */
	@Transaction
	UserSoulMap lightUserSoulMap(int userId,int soulId);
	/**
	 * 用户看见战魂
	 * @param userId
	 * @param soulId
	 * @return
	 */
	@Transaction
	UserSoulMap seeUserSoulMap(int userId,int soulId);
	/**
	 * 用户看见战魂
	 * @param userId
	 * @param soulIds
	 * @return
	 */
	@Transaction
	List<UserSoulMap> seeUserSoulMap(int userId,Collection<Integer> soulIds);
	
	/***
	 * 用户图鉴
	 * @param userId
	 * @param state
	 * @return
	 */
	List<UserSoulMapVO> queryUserSoulMap(int userId);
	
	/***
	 * 根据状态查看用户图鉴
	 * @param userId
	 * @param state
	 * @return
	 */
	List<UserSoulMap> queryUserSoulMapByeState(int userId,int state);
	/**
	 * 设置战魂保护状态
	 * @param userId 用户编号
	 * @param userSoulId 战魂编号
	 * @param safe 是否为保护
	 */
	void updateSoulSafe(int userId,long userSoulId,boolean safe);
	/**
	 * 重塑战魂
	 * @param userId
	 * @param userSoulIds
	 * @return
	 */
	@Transaction
	UserSoulOptResultVO reShapeSoul(int userId,List<Long> userSoulIds);
	/**
	 * 新手选强化素材
	 * @param userId
	 * @param choose
	 * @return
	 */
	@Transaction
	UserSoulVO getGuideStrengSoul(int userId,int choose);
	
	/**
	 * 新手战魂进化
	 * @param userId 用户编号
	 * @param userSoulId 要进化战魂的编号
	 * @param soulIds 进化战魂的素材
	 * @return 操作结果
	 */
	@Transaction
	UserSoulOptResultVO guideSoulEvolution(int userId,long userSoulId);
	/**
	 * 拥有的战魂集合
	 * @param userId
	 * @return
	 */
	List<UserSoul> getUserSoulListFromCache(int userId);
	
}
