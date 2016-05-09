package com.ks.game.handler;

import com.ks.app.Application;
import com.ks.handler.GameHandler;
import com.ks.protocol.MainCmd;
import com.ks.protocol.SubCmd;
import com.ks.protocol.main.MainCMD;
import com.ks.protocol.sub.BudingCMD;

@MainCmd(mainCmd=MainCMD.BUDING)
public class BudingHandler extends ActionAdapter{
	
	/**
	 * 获取用户建筑
	 * @param handler 用户
	 */
	@SubCmd(subCmd=BudingCMD.GAIN_USER_BUDINGS)
	public void gainUserBudings(GameHandler handler){
		Application.sendMessage(handler.getChannel(), handler.getHead(), 
				userBudingAction().gainUserBudings(handler.getPlayer().getUserId()));
	}
	/**
	 * 升级建筑
	 * @param handler 用户
	 * @param budingId 建筑编号
	 * @param gold 金钱
	 */
	@SubCmd(subCmd=BudingCMD.LEVEL_UP_BUDING,args={"int","int"})
	public void levelUpBuding(GameHandler handler,int budingId,int gold){
		Application.sendMessage(handler.getChannel(),handler.getHead(),
				userBudingAction().levelUpBuding(handler.getPlayer().getUserId(), budingId, gold));
	}
	
	/**
	 * 收集建筑
	 * @param handler 用户
	 * @param budingId 建筑编号
	 * @param count 次数
	 */
	@SubCmd(subCmd=BudingCMD.COLLECT_BUDING,args={"int","int"})
	public void collectBuding(GameHandler handler,int budingId,int count){
		Application.sendMessage(handler.getChannel(), handler.getHead(), 
				userBudingAction().collectBuding(handler.getPlayer().getUserId(), budingId, count));
	}
	
	/**
	 * 收集建筑
	 * @param handler 用户
	 * @param budingId 建筑编号
	 * @param count 次数
	 */
	@SubCmd(subCmd=BudingCMD.COLLECT_SINGLEL_BUDING_ALL_PROP,args={"int"})
	public void collectSinleBudingAllProp(GameHandler handler,int budingId){
		Application.sendMessage(handler.getChannel(), handler.getHead(), 
				userBudingAction().collectBuding(handler.getPlayer().getUserId(), budingId));
	}
}
