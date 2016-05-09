package com.ks.logic.service;

import java.util.List;

import com.ks.access.Transaction;
import com.ks.protocol.vo.dungeon.FightEndResultVO;
import com.ks.protocol.vo.dungeon.FightResultVO;
import com.ks.protocol.vo.mission.UserAwardVO;
import com.ks.protocol.vo.tower.ClimbTowerAwardRecordVO;
import com.ks.protocol.vo.tower.ClimbTowerUserVO;


/**
 * 爬塔试炼战斗
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2015年11月17日
 */
public interface ClimbTowerFightService {
	
	/**
	 * 查询指定用户层数的爬塔信息
	 * @param userId 用户编号
	 * @param towerFloor 第几层塔
	 * @return
	 */
	ClimbTowerUserVO findByTowerFloor(int userId, int towerFloor);
	
	/**
	 * 查询用户所有爬塔信息
	 * @param userId 用户编号
	 * @return
	 */
	@Transaction
	List<ClimbTowerUserVO> queryClimbTower(int userId);
	
	/**
	 * 爬塔开始战斗
	 * @param userId 用户编号
	 * @param towerFloor 第几层塔
	 * @param teamId
	 */
	@Transaction
	FightResultVO startFight(int userId, int towerFloor, byte teamId);
	
	/**
	 * 爬塔试炼结束战斗 
	 * @param userId 用户编号
	 * @param starNum 通关星星数量
	 * @param pass 是否通关
	 * @return
	 */
	@Transaction
	FightEndResultVO endFigth(int userId, short starNum, boolean pass);
	
	/**
	 * 购买额外的爬塔次数
	 * @param userId 用户编号
	 * @param count 购买次数
	 * @param towerFloor 第几层塔
	 * @return 剩余魂钻数量
	 */
	@Transaction
	int buyClimbTowerExtraCount(int userId, int towerFloor, int count);
	
	/**
	 * 购买一次指定层额外的爬塔次数
	 * @param userId 用户编号
	 * @param towerFloor 第几层塔
	 * @return 剩余魂钻数量
	 */ 
	@Transaction
	int buyClimbTowerExtraCount(int userId, int towerFloor);
	
	/**
	 * 扫荡
	 * @param userId 用户编号
	 * @param towerFloor 第几层塔
	 * @param teamId
	 * @return
	 */
	@Transaction
	FightEndResultVO sweep(int userId, int towerFloor, byte teamId);
	
	/**
	 * 领取集星奖励
	 * @param userId 用户编号
	 * @param starRegion 星星区域
	 * @param startNum 星星数量
	 * @return
	 */
	@Transaction
	UserAwardVO getStarAward(int userId, int starRegion, int starNum);
	
	/**
	 * 查询用户每个区域所有星星记录
	 * @param userId
	 * @return
	 */
	List<ClimbTowerAwardRecordVO> queryClimbTowerUserRegionStar(int userId);
}