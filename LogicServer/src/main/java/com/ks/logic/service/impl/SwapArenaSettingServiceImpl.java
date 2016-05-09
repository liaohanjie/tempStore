package com.ks.logic.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.ks.logic.cache.GameCache;
import com.ks.logic.service.BaseService;
import com.ks.logic.service.SwapArenaSettingService;
import com.ks.model.logger.LoggerType;
import com.ks.model.robot.Robot;
import com.ks.model.robot.TeamTemplate;
import com.ks.model.swaparena.SwapArena;
import com.ks.model.swaparena.SwapArenaBuySetting;
import com.ks.model.swaparena.SwapArenaRewardSetting;
import com.ks.model.user.User;
import com.ks.model.user.UserSoul;
import com.ks.model.user.UserTeam;

/**
 * 交换竞技场配置服务
 * 
 * @author hanjie.l
 * 
 */
public class SwapArenaSettingServiceImpl extends BaseService implements SwapArenaSettingService {
	
	@Override
	public void renameRobots(){
		List<Robot> queryAllRobot = robotDAO.queryAllRobot();
		for (Robot robot : queryAllRobot) {
			User user = userDAO.findUserByUsername("机器"+robot.getId()+"号", 10);
			if(user != null){
				userTeamDAO.deleteUserCapCache(user.getUserId());
				user.setPlayerName(robot.getPlayerName());
				userDAO.addUserCache(user);
				userDAO.updatePlayerName(user.getUserId(), user.getPlayerName());
			}
		}
	}

	@Override
	public void initRobots() {
		if(swapArenaDAO.getSwapArenaCount() <= 1){
			List<Robot> queryAllRobot = robotDAO.queryAllRobot();
			for (int i=0; i<queryAllRobot.size(); i++) {
				Robot robot = queryAllRobot.get(i);
				User user = null;
				user = userDAO.findUserByPlayername(robot.getPlayerName());
				if(user == null){
				    user = new User();
					user.setPartner(10);
					user.setUsername("机器"+robot.getId()+"号");
					user.setPlayerName(robot.getPlayerName());
					user.setLevel(robot.getLevel());
					user = userDAO.addUser(user);
				}
				

				TeamTemplate teamTemplate = GameCache.getTeamTemplateById(robot.getTemplateId());

				// 创建战魂
				List<UserSoul> souls = new ArrayList<UserSoul>();
				UserSoul userSoul1 = userSoulService.addUserSoul(user, teamTemplate.getSoulId1(), teamTemplate.getLevel1(), LoggerType.TYPE_机器初始化);
				souls.add(userSoul1);
				UserSoul userSoul2 = userSoulService.addUserSoul(user, teamTemplate.getSoulId2(), teamTemplate.getLevel2(), LoggerType.TYPE_机器初始化);
				souls.add(userSoul2);
				UserSoul userSoul3 = userSoulService.addUserSoul(user, teamTemplate.getSoulId3(), teamTemplate.getLevel3(), LoggerType.TYPE_机器初始化);
				souls.add(userSoul3);
				UserSoul userSoul4 = userSoulService.addUserSoul(user, teamTemplate.getSoulId4(), teamTemplate.getLevel4(), LoggerType.TYPE_机器初始化);
				souls.add(userSoul4);
				UserSoul userSoul5 = userSoulService.addUserSoul(user, teamTemplate.getSoulId5(), teamTemplate.getLevel5(), LoggerType.TYPE_机器初始化);
				souls.add(userSoul5);

				List<UserTeam> uts = userTeamDAO.getUserTeamCache(user.getUserId());
				if (uts.size() == 0) {
					UserTeam ut = new UserTeam();
					ut.setTeamId((byte) 1);
					ut.setUserId(user.getUserId());
					ut.setCap((byte) 1);
					ut.setPos(new ArrayList<Long>());
					for (int j = 0; j < 5; j++) {
						ut.getPos().add(souls.get(j).getId());
					}
					ut.setCreateTime(new Date());
					ut.setUpdateTime(new Date());
					uts.add(ut);
					userTeamDAO.addUserTeams(uts);
				}
				
				//初始化队长
				userTeamService.initUserCap(user, userSoul1);
				
				
				SwapArena arena = new SwapArena();
				arena.setId(robot.getId());
				arena.setRank(robot.getId());
				arena.setUserId(user.getUserId());
				arena.setRobot(true);
				
				swapArenaDAO.addSwapArena(arena);
			}
		}
	}
	
	@Override
	public void clearRobot(){
		List<User> findAllUser = userDAO.findAllUser();
		for(User user : findAllUser){
			if(user.getPlayerName().contains("机器")){
				userDAO.delUserCache(user.getUserId());
				userDAO.removeUserLevel(user.getUserId());
				userSoulService.clearUserSoul(user.getUserId());
				userTeamService.clearUserTeamCache(user.getUserId());
				userTeamDAO.deleteUserCapCache(user.getUserId());
			}
		}
	}

	@Override
	public List<Robot> findAllRobot() {
		return robotDAO.queryAllRobot();
	}

	@Override
	public List<TeamTemplate> findAllTeamTemplate() {
		return robotDAO.queryAllTeamTemplate();
	}

	@Override
	public List<SwapArenaRewardSetting> findAllReward() {
		return robotDAO.queryAllRankReward();
	}

	@Override
	public List<SwapArenaBuySetting> findAllSwapBuy() {
		return robotDAO.queryAllBuyCountSetting();
	}
}
