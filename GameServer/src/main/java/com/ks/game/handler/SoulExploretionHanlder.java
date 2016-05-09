package com.ks.game.handler;

import com.ks.app.Application;
import com.ks.handler.GameHandler;
import com.ks.protocol.MainCmd;
import com.ks.protocol.SubCmd;
import com.ks.protocol.main.MainCMD;
import com.ks.protocol.sub.SoulExploretionCMD;

/**
 * @author fengpeng E-mail:fengpeng_15@163.com
 * @version 创建时间：2014年8月11日 下午3:03:42
 * 类说明
 */
@MainCmd(mainCmd=MainCMD.MAIN_探索)
public class SoulExploretionHanlder extends ActionAdapter {
	/**
	 * 进入探索界面
	 * @param handler
	 */
	@SubCmd(subCmd = SoulExploretionCMD.SOULEXPLORETION_探索战魂信息)
	public void enterSoulExploretion(GameHandler handler) {
		Application.sendMessage(handler.getChannel(), handler.getHead(),
				soulExploretionAction().getSoulExploretionList(handler.getPlayer().getUserId()));
	}
	
	/**
	 * 添加探索战魂
	 * @param handler
	 */
	@SubCmd(subCmd = SoulExploretionCMD.SOULEXPLORETION_添加探索战魂,args={"long","int","int"})
	public void addSoulExploretion(GameHandler handler,long userSoulId,int hour,int teamId) {
		Application.sendMessage(handler.getChannel(), handler.getHead(),
				soulExploretionAction().addSoulExploretion(handler.getPlayer().getUserId(),userSoulId,hour,teamId));
	}
	
	/**
	 * 领取探索奖励
	 * 
	 * @param handler
	 */
	@SubCmd(subCmd = SoulExploretionCMD.SOULEXPLORETION_领取探索结果,args={"long"})
	public void exploretionAward(GameHandler handler,long userSoulId) {
		Application.sendMessage(handler.getChannel(), handler.getHead(),
				soulExploretionAction().exploretionAward(handler.getPlayer().getUserId(),userSoulId));
	}
	
	/**
	 * 快速结束领取探索奖励
	 * 
	 * @param handler
	 */
	@SubCmd(subCmd = SoulExploretionCMD.SOULEXPLORETION_快速结束探索,args={"long"})
	public void quicklyExploretionAward(GameHandler handler,long userSoulId) {
		Application.sendMessage(handler.getChannel(), handler.getHead(),
				soulExploretionAction().quicklyExploretionAward(handler.getPlayer().getUserId(),userSoulId));
	}
}
