package com.ks.logic.action;

import java.util.List;

import com.ks.action.logic.ClimbTowerAction;
import com.ks.logic.service.ClimbTowerFightService;
import com.ks.logic.service.ServiceFactory;
import com.ks.protocol.vo.dungeon.FightEndResultVO;
import com.ks.protocol.vo.dungeon.FightResultVO;
import com.ks.protocol.vo.mission.UserAwardVO;
import com.ks.protocol.vo.tower.ClimbTowerAwardRecordVO;
import com.ks.protocol.vo.tower.ClimbTowerUserVO;

public class ClimbTowerActionImpl implements ClimbTowerAction {
	
	private static ClimbTowerFightService climbTowerFightService = ServiceFactory.getService(ClimbTowerFightService.class);
	
	@Override
    public ClimbTowerUserVO findByTowerFloor(int userId, int towerFloor) {
	    return climbTowerFightService.findByTowerFloor(userId, towerFloor);
    }

	@Override
    public List<ClimbTowerUserVO> queryClimbTower(int userId) {
	    return climbTowerFightService.queryClimbTower(userId);
    }

	@Override
    public FightResultVO startFight(int userId, int towerFloor, byte teamId) {
	    return climbTowerFightService.startFight(userId, towerFloor, teamId);
    }

	@Override
    public FightEndResultVO endFight(int userId, short starNum, boolean pass) {
	    return climbTowerFightService.endFigth(userId, starNum, pass);
    }

	@Override
    public int buyClimbTowerExtraCount(int userId, int towerFloor, int count) {
	    return climbTowerFightService.buyClimbTowerExtraCount(userId, count);
    }

	@Override
    public int buyClimbTowerExtraCount(int userId, int towerFloor) {
	    return climbTowerFightService.buyClimbTowerExtraCount(userId, towerFloor, 1);
    }

	@Override
    public FightEndResultVO sweep(int userId, int towerFloor, byte teamId) {
	    return climbTowerFightService.sweep(userId, towerFloor, teamId);
    }

	@Override
    public UserAwardVO getStarAward(int userId, int starRegion, int starNum) {
	    return climbTowerFightService.getStarAward(userId, starRegion, starNum);
    }

	@Override
    public List<ClimbTowerAwardRecordVO> queryClimbTowerUserRegionStar(int userId) {
	    return climbTowerFightService.queryClimbTowerUserRegionStar(userId);
    }

}
