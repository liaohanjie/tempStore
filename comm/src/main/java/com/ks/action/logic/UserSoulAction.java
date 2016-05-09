package com.ks.action.logic;

import java.util.List;

import com.ks.access.Transaction;
import com.ks.protocol.vo.soul.CallSoulResultVO;
import com.ks.protocol.vo.soul.UserSoulInfoVO;
import com.ks.protocol.vo.soul.UserSoulMapVO;
import com.ks.protocol.vo.soul.UserSoulOptResultVO;
import com.ks.protocol.vo.user.UserSoulVO;

/**
 * 用户战魂
 * @author ks
 */
public interface UserSoulAction {
	
	/**
	 * 强化战魂
	 * @param userSoulId 要强化的战魂
	 * @param soulIds 被强化的战魂
	 * @return 强化结果
	 */
	UserSoulOptResultVO strengUserSoul(int userId,long userSoulId,List<Long> soulIds);
	
	/**
	 * 战魂进化
	 * @param userId 用户编号
	 * @param userSoulId 要进化战魂的编号
	 * @param soulIds 进化战魂的素材
	 * @return 操作结果
	 */
	UserSoulOptResultVO soulEvolution(int userId,long userSoulId,List<Long> soulIds);
	
	/**
	 * 卖出战魂
	 * @param userId 用户编号
	 * @param userSoulIds 卖出的战魂
	 * @return 卖出多少钱
	 */
	int sellSoul(int userId,List<Long> userSoulIds);
	/**
	 * 
	 * @param userId 用户编号
	 * @param type 召唤类型
	 * @param mum 召换次数
	 * @return
	 */
	CallSoulResultVO callSoul(int userId,int type,int mum);
	
	/**
	 * 获得用户战魂信息
	 * @param userId 用户编号
	 * @return 战魂信息
	 */
	UserSoulInfoVO gainUserSoulInfo(int userId);
	/**
	 * 增加战魂仓库容量
	 * @param userId 用户编号
	 * @return 剩余金钱
	 */
	int addSoulCapacity(int userId);
	/**
	 * 查看用户图鉴
	 * @param userId
	 * @return
	 */
	List<UserSoulMapVO>  queryUserSoulMap(int userId);
	
	/**
	 * 重塑战魂
	 * @param userId
	 * @param userSoulIds
	 * @return
	 */
	@Transaction
	UserSoulOptResultVO reShapeSoul(int userId,List<Long> userSoulIds);
	
	/**
	 * 设置战魂保护状态
	 * @param userId 用户编号
	 * @param userSoulId 战魂编号
	 * @param safe 是否为保护
	 */
	void updateSoulSafe(int userId,long userSoulId,boolean safe);
	
	/**
	 * 获取新手
	 * @param userId
	 * @param choose
	 * @return
	 */
	public UserSoulVO getGuideStrengSoul(int userId, int choose);
	
	/**
	 * 新手战魂进化
	 * @param userId 用户编号
	 * @param userSoulId 要进化战魂的编号
	 * @param soulIds 进化战魂的素材
	 * @return 操作结果
	 */
	@Transaction
	UserSoulOptResultVO guideSoulEvolution(int userId,long userSoulId);
}
