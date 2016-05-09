package com.ks.game.handler;

import com.ks.app.Application;
import com.ks.handler.GameHandler;
import com.ks.protocol.MainCmd;
import com.ks.protocol.SubCmd;
import com.ks.protocol.main.MainCMD;
import com.ks.protocol.sub.ClimbTowerCMD;

@MainCmd(mainCmd=MainCMD.CLIMB_TOWER)
public class ClimbTowerHandler extends ActionAdapter {
	
	/**
	 * 查询指定用户层数的爬塔信息
	 * @param handler
	 * @param towerFloor 第几层
	 */
	@SubCmd(subCmd=ClimbTowerCMD.QUERY_CLIMB_TOWER_FLOOR, args={"int"})
	public void findByTowerFloor(GameHandler handler, int towerFloor){
		Application.sendMessage(handler.getChannel(), handler.getHead(), climbTowerAction().findByTowerFloor(handler.getPlayer().getUserId(), towerFloor));
	}
	
	/**
	 * 查询用户所有爬塔信息
	 * @param handler
	 */
	@SubCmd(subCmd=ClimbTowerCMD.QUERY_ALL_CLIMB_TOWER_FLOOR)
	public void queryClimbTower(GameHandler handler){
		Application.sendMessage(handler.getChannel(), handler.getHead(), climbTowerAction().queryClimbTower(handler.getPlayer().getUserId()));
	}
	
	/**
	 * 开始战斗
	 * @param handler
	 * @param towerFloor
	 * @param teamId
	 */
	@SubCmd(subCmd=ClimbTowerCMD.START_FIGHT, args={"int", "byte"})
	public void startFight(GameHandler handler, int towerFloor, byte teamId){
		Application.sendMessage(handler.getChannel(), handler.getHead(), climbTowerAction().startFight(handler.getPlayer().getUserId(), towerFloor, teamId));
	}
	
	/**
	 * 结束战斗
	 * @param handler
	 * @param towerFloor
	 * @param teamId
	 */
	@SubCmd(subCmd=ClimbTowerCMD.END_FIGHT, args={"short", "boolean"})
	public void endFight(GameHandler handler, short starNum, boolean pass){
		Application.sendMessage(handler.getChannel(), handler.getHead(), climbTowerAction().endFight(handler.getPlayer().getUserId(), starNum, pass));
	}
	
	/**
	 * 购买一次指定层额外的爬塔次数
	 * @param handler
	 * @param towerFloor
	 */
	@SubCmd(subCmd=ClimbTowerCMD.BUG_CLIMB_TOWER_EXTRA_COUNT, args={"int"})
	public void buyClimbTowerExtraCount(GameHandler handler, int towerFloor){
		Application.sendMessage(handler.getChannel(), handler.getHead(), climbTowerAction().buyClimbTowerExtraCount(handler.getPlayer().getUserId(), towerFloor));
	}
	
	/**
	 * 扫荡
	 * @param handler
	 * @param towerFloor
	 * @param teamId
	 */
	@SubCmd(subCmd=ClimbTowerCMD.SWEEP, args={"int", "byte"})
	public void sweep(GameHandler handler, int towerFloor, byte teamId){
		Application.sendMessage(handler.getChannel(), handler.getHead(), climbTowerAction().sweep(handler.getPlayer().getUserId(), towerFloor, teamId));
	}
	
	/**
	 * 获取星星奖励
	 * @param handler
	 * @param startNum
	 */
	@SubCmd(subCmd=ClimbTowerCMD.GET_STAR_AWARD, args={"int", "int"})
	public void getStarAward(GameHandler handler, int starRegion, int starNum){
		Application.sendMessage(handler.getChannel(), handler.getHead(), climbTowerAction().getStarAward(handler.getPlayer().getUserId(),starRegion, starNum));
	}
	
	/**
	 * 查询用户每个区域最大记录
	 */
	@SubCmd(subCmd=ClimbTowerCMD.QUERY_EVERY_REGION_STAR_AWARD_RECORD)
	public void queryEveryRegionMaxStarAwardRecord(GameHandler handler){
		Application.sendMessage(handler.getChannel(), handler.getHead(), climbTowerAction().queryClimbTowerUserRegionStar(handler.getPlayer().getUserId()));
	}
}
