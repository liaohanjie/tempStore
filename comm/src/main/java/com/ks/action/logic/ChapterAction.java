package com.ks.action.logic;

import java.util.List;

import com.ks.access.Transaction;
import com.ks.protocol.vo.dungeon.FightEndResultVO;
import com.ks.protocol.vo.dungeon.FightResultVO;
import com.ks.protocol.vo.dungeon.MonsterAwardVO;
import com.ks.protocol.vo.mission.UserAwardVO;
import com.ks.protocol.vo.user.UserBuffVO;
import com.ks.protocol.vo.user.UserChapterVO;

/**
 * 副本
 * @author ks
 */
public interface ChapterAction {
	/**
	 * 查询所有副本
	 * @param userId
	 * @return
	 */
	UserChapterVO queryUserChapter(int userId);
	
	/**
	 * 查询用户所有通关副本
	 * @param userId
	 * @return
	 */
	List<UserChapterVO> queryUserChapterList(int userId);
	
	/**
	 * 开始战斗
	 * @param userId 用户编号
	 * @param dungeonId 副本编号
	 * @param checkpoint 段编号
	 * @param friendId 好友编号
	 * @param teamId 队伍编号
	 * @return 战斗结果
	 */
	FightResultVO startFight(int userId,int chapterId,int friendId,byte teamId);
	/**
	 * 结束关卡
	 * @param userId 用户编号
	 * @param pass 是否通过
	 */
	FightEndResultVO endFight(int userId,boolean pass,boolean hasJoin);
	/**
	 * 开箱子
	 * @param userId 用户编号
	 * @param boxId 盒子编号
	 * @return 箱子中开出的物品
	 */
	MonsterAwardVO openBox(int userId,int boxId);
	
	/**
	 * 复活
	 * @param userId 用户编号
	 * @return 使用了的物品格子
	 */
	int resurrection(int userId);
	
	/**
	 * 使用副本道具
	 * @param userId
	 * @param propId
	 * @return
	 */
	List<UserBuffVO> userBakProp(int userId,int propId);
	
	@Transaction
	FightEndResultVO sweep(int userId,int chapterId,int count,byte teamId);
	
	List<UserChapterVO> queryUserActivityChapter(int userId);
	
	/**
	 * 获取章节宝箱记录
	 * @param userId
	 * @return
	 */
	List<Integer> getChapterChestRecrds(int userId);
	
	/**
	 * 领取章节宝箱
	 * @param userId
	 * @param chapterId
	 * @return
	 */
	@Transaction
	UserAwardVO getChapterChestAward(int userId, int chapterId);

	/**
	 * 购买副本挑战次数
	 * 
	 * @param userId
	 * @return
	 */
	@Transaction
	UserChapterVO buyChapterFightCount(int userId, int chapterId);
}
