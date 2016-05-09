package com.ks.logic.service;

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
 * 
 * @author ks
 */
public interface UserChapterService {
	/**
	 * 查询所有副本
	 * 
	 * @param userId
	 * @return
	 */
	@Transaction
	UserChapterVO queryUserChapter(int userId);

	/**
	 * 开始战斗
	 * 
	 * @param userId
	 *            用户编号
	 * @param dungeonId
	 *            副本编号
	 * @param checkpoint
	 *            段编号
	 * @param friendId
	 *            好友编号
	 * @param teamId
	 *            队伍编号
	 * @return 战斗结果
	 */
	@Transaction
	FightResultVO startFight(int userId, int chapterId, int friendId, byte teamId);

	/**
	 * 结束战斗
	 * 
	 * @param userId
	 *            用户编号
	 * @param pass
	 *            是否通过
	 * @return 战斗结果
	 */
	@Transaction
	FightEndResultVO endFight(int userId, boolean pass, boolean hasJoin);

	/**
	 * 开箱子
	 * 
	 * @param userId
	 *            用户编号
	 * @param boxId
	 *            箱子编号
	 * @return 箱子开出的物品
	 */
	MonsterAwardVO openBox(int userId, int boxId);

	/**
	 * 复活
	 * 
	 * @param userId
	 *            用户编号
	 * @return 使用了的物品格子
	 */
	@Transaction
	int resurrection(int userId);

	/**
	 * 使用副本钥匙
	 * 
	 * @param propId
	 * @return
	 */
	@Transaction
	List<UserBuffVO> userBakChapterProp(int userId, int propId);

	/**
	 * 扫荡
	 * 
	 * @param userId
	 *            ,
	 * @param chapterId
	 *            章节id
	 * @param count
	 *            扫荡次数
	 * @return 反回已用扫荡次数
	 */
	@Transaction
	FightEndResultVO sweep(int userId, int chapterId, int count, byte teamId);

	/**
	 * 用户已打过的活动章节
	 * 
	 * @param userId
	 * @return
	 */
	List<UserChapterVO> queryUserActivityChapter(int userId);

	/**
	 * 获取章节宝箱记录
	 * 
	 * @param userId
	 * @return
	 */
	List<Integer> getChapterChestRecrds(int userId);

	/**
	 * 获得冒险宝箱奖励
	 * 
	 * @param userId
	 * @param chapterId
	 * @return
	 */
	@Transaction
	UserAwardVO getChapterChestAward(int userId, int chapterId);

	/**
	 * 查询用户所有通关副本
	 * 
	 * @param userId
	 * @return
	 */
	@Transaction
	List<UserChapterVO> queryUserChapterList(int userId);

	/**
	 * 重置副本当日数据
	 * 
	 * @param userId
	 */
	@Transaction
	void reset(int userId);

	/**
	 * 购买副本挑战次数
	 * 
	 * @param userId
	 * @return
	 */
	@Transaction
	UserChapterVO buyChapterFightCount(int userId, int chapterId);
}
