package com.ks.game.handler;

import java.util.List;

import com.ks.app.Application;
import com.ks.handler.GameHandler;
import com.ks.protocol.MainCmd;
import com.ks.protocol.SubCmd;
import com.ks.protocol.main.MainCMD;
import com.ks.protocol.sub.UserTeamCMD;
import com.ks.protocol.vo.user.UserTeamVO;

/**
 * 用户队伍
 * @author ks
 */
@MainCmd(mainCmd=MainCMD.USER_TEAM)
public class UserTeamHandler extends ActionAdapter{
	
	/**
	 * 用户队伍
	 * @param handler
	 * @param teams
	 */
	@SubCmd(subCmd=UserTeamCMD.UPDATE_USER_TEAM,args={"userTeam_true","byte"})
	public void updateUserTeam(GameHandler handler,List<UserTeamVO> teams,byte currTeamId){
		userTeamAction().updateUserTeam(handler.getPlayer().getUserId(), teams,currTeamId);
		Application.sendMessage(handler.getChannel(), handler.getHead());
	}
	
	/**
	 * 查找当前队伍信息
	 * @param handler
	 * @param teams
	 */
	@SubCmd(subCmd=UserTeamCMD.FIND_USER_CURRENT_TEAM, args={"int"})
	public void findCurrencyTeam(GameHandler handler, int userId){
		Application.sendMessage(handler.getChannel(), handler.getHead(), userTeamAction().findUserCurrentTeam(userId));
	}
	
	/**
	 * 查看玩家队长信息
	 * @param handler
	 * @param userId
	 */
	@SubCmd(subCmd=UserTeamCMD.FIND_USER_CAP, args={"int"})
	public void findUserCap(GameHandler handler, int userId){
		Application.sendMessage(handler.getChannel(), handler.getHead(), userTeamAction().findUserCap(userId));
	}
}
