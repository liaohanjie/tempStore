package com.ks.action.logic;

import java.util.List;

import com.ks.protocol.vo.dungeon.FightEndResultVO;
import com.ks.protocol.vo.explora.SoulExploretionVO;

/**
 * @author fengpeng E-mail:fengpeng_15@163.com
 * 
 * @version 创建时间：2014年8月11日 下午2:39:10
 * 
 * 探索接口
 */
public interface SoulExploretionAction {
	
	/**
	 * 添加探索战魂
	 * @param soulId
	 * @param hour
	 * @return
	 */
	SoulExploretionVO addSoulExploretion(int userId,long soulId,int hour,int teamId);
	
	/**
	 * 探索中的战魂
	 * @param userId
	 * @return
	 */
	List<SoulExploretionVO> getSoulExploretionList(int userId);
	/**
	 * 领取探索奖励
	 * @param userId
	 * @param soulId
	 * @return
	 */
	public FightEndResultVO exploretionAward(int userId,long soulId);
	/**
	 * 快速结束探索
	 * @param userId
	 * @param soulId
	 * @return
	 */
	public FightEndResultVO quicklyExploretionAward(int userId, long soulId);

}
