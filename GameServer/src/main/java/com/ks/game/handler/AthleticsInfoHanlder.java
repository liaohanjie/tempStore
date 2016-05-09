package com.ks.game.handler;

import com.ks.app.Application;
import com.ks.handler.GameHandler;
import com.ks.protocol.MainCmd;
import com.ks.protocol.SubCmd;
import com.ks.protocol.main.MainCMD;
import com.ks.protocol.sub.AthleticsInfoCMD;

@MainCmd(mainCmd=MainCMD.PVP_ATHLETICSINFO_战斗)
public class AthleticsInfoHanlder extends ActionAdapter {
	/**
	 * 获取匹配的对手信息
	 * @param handler
	 */
	@SubCmd(subCmd = AthleticsInfoCMD.ATHLETICSINFO_匹配对手信息)
	public void gainMatchAthleticsInfo(GameHandler handler) {
		Application.sendMessage(handler.getChannel(), handler.getHead(),
				athleticsInfoAction().getMatchUser(handler.getPlayer().getUserId()));
	}
	/**
	 * 获取排行榜的信息
	 * @param handler
	 */
	@SubCmd(subCmd = AthleticsInfoCMD.ATHLETICSINFO_排行榜信息)
	public void getAthleticsInfo(GameHandler handler) {
		Application.sendMessage(handler.getChannel(), handler.getHead(),
				athleticsInfoAction().getAthleticsInfo(handler.getPlayer().getUserId()));
	}
	
	/**
	 * 进入竞技场
	 * @param handler
	 */
	@SubCmd(subCmd = AthleticsInfoCMD.ATHLETICSINFO_进入竞技场)
	public void enterArnea(GameHandler handler) {
		Application.sendMessage(handler.getChannel(), handler.getHead(),
				athleticsInfoAction().enterArnea(handler.getPlayer().getUserId()));
	}
	/**
	 * 开始战斗
	 * @param handler
	 * @param matchId
	 */
	@SubCmd(subCmd = AthleticsInfoCMD.ATHLETICSINFO_开始战斗,args={"int"})
	public void startArneaPK(GameHandler handler,int matchId) {
		Application.sendMessage(handler.getChannel(), handler.getHead(),
				athleticsInfoAction().startArneaPK(handler.getPlayer().getUserId(),matchId));
	}
	/**
	 * 花钱购买竞技点
	 * @param handler
	 */
	@SubCmd(subCmd = AthleticsInfoCMD.ATHLETICSINFO_花钱购买竞技点)
	public void regainAthleticsPoint(GameHandler handler) {
		int currency =athleticsInfoAction().regainAthleticsPoint(handler.getPlayer().getUserId());
		Application.sendMessage(handler.getChannel(), handler.getHead(),currency);
	}
	
}
