package com.ks.logic.service;

import java.util.List;

import com.ks.access.Transaction;
import com.ks.model.explora.ExplorationAward;
import com.ks.model.explora.ExplorationAwardExp;
import com.ks.protocol.vo.dungeon.FightEndResultVO;
import com.ks.protocol.vo.explora.SoulExploretionVO;

/**
 * @author fengpeng E-mail:fengpeng_15@163.com
 * @version 创建时间：2014年8月8日 下午5:41:50
 * 类说明
 */
public interface SoulExploretionService {
	/**
	 * 添加探索战魂
	 * @param soulId
	 * @param hour
	 * @return
	 */
	@Transaction
	SoulExploretionVO addSoulExploretion(int userId,long soulId,int hour,int teamId);
	
	/**
	 * 探索中的战魂
	 * @param userId
	 * @return
	 */
	List<SoulExploretionVO> getSoulExploretionList(int userId);
	/**
	 * 探索奖励集合
	 * @return
	 */
	List<ExplorationAward> querAwardList();
	/**
	 * 探索结束后领取奖励
	 * @param userId
	 * @param soulId
	 * @return
	 */
	@Transaction
	public FightEndResultVO exploretionAward(int userId,long soulId);
	
	/**
	 * 探索奖励经验
	 * @return
	 */
	List<ExplorationAwardExp> querAwardExpList();
	
	/**
	 * 快速领取奖励
	 * @param userId
	 * @param soulId
	 * @return
	 */
	@Transaction
	public FightEndResultVO quicklyExploretionAward(int userId,long soulId);
}
