package com.ks.logic.service;

import java.util.List;

import com.ks.access.Transaction;
import com.ks.model.robot.Robot;
import com.ks.model.robot.TeamTemplate;
import com.ks.model.swaparena.SwapArenaBuySetting;
import com.ks.model.swaparena.SwapArenaRewardSetting;

/**
 * 交换竞技场配置服务
 * @author hanjie.l
 *
 */
public interface SwapArenaSettingService {
	
	/**
	 * 重命名机器人
	 */
	@Transaction
	public void renameRobots();
	
	/**
	 * 初始化机器人
	 */
	@Transaction
	public void initRobots();
	
	/**
	 * 清除机器缓存
	 */
	@Transaction
	public void clearRobot();
	
	
	/**
	 * 查询所有机器人
	 * @return
	 */
	public List<Robot> findAllRobot();
	
	
	/**
	 * 查找所有模版
	 * @return
	 */
	public List<TeamTemplate> findAllTeamTemplate();
	
	
	/**
	 * 查找所有奖励
	 * @return
	 */
	public List<SwapArenaRewardSetting> findAllReward();
	
	/**
	 * 查找所有购买规则
	 * @return
	 */
	public List<SwapArenaBuySetting> findAllSwapBuy();

}
