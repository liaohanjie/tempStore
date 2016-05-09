package com.ks.game.handler;

import com.ks.app.Application;
import com.ks.handler.GameHandler;
import com.ks.protocol.MainCmd;
import com.ks.protocol.SubCmd;
import com.ks.protocol.main.MainCMD;
import com.ks.protocol.sub.UserMissionCMD;

/**
 * @author fengpeng E-mail:fengpeng_15@163.com
 * @version 创建时间：2014年8月11日 下午3:03:42
 * 类说明
 */
@MainCmd(mainCmd=MainCMD.USER_MISSION)
public class UserMissionHanlder extends ActionAdapter {
	/**
	 * 进入每日任务页面
	 * @param handler
	 */
	@SubCmd(subCmd = UserMissionCMD.USER_MISSION_任务信息)
	public void enterSoulExploretion(GameHandler handler) {
		Application.sendMessage(handler.getChannel(), handler.getHead(),
				userMissionAction().getUserMissions(handler.getPlayer().getUserId()));
	}
	
	
	/**
	 * 领取每日任务奖励
	 * 
	 * @param handler
	 */
	@SubCmd(subCmd = UserMissionCMD.USER_MISSION_领取任务奖励,args={"int"})
	public void exploretionAward(GameHandler handler,int missionId) {
		Application.sendMessage(handler.getChannel(), handler.getHead(),
				userMissionAction().missionAward(handler.getPlayer().getUserId(),missionId));
	}
	
	/**
	 * 完成分享任务
	 * 
	 * @param handler
	 */
	@SubCmd(subCmd = UserMissionCMD.FINISH_SHARETASK_完成分享任务)
	public void finishShareTask(GameHandler handler) {
		userMissionAction().finishShareTask(handler.getPlayer().getUserId());
		Application.sendMessage(handler.getChannel(), handler.getHead());
	}
}
