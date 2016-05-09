package com.ks.game.handler;

import java.util.List;

import com.ks.app.Application;
import com.ks.handler.GameHandler;
import com.ks.protocol.MainCmd;
import com.ks.protocol.SubCmd;
import com.ks.protocol.main.MainCMD;
import com.ks.protocol.sub.UserSoulCMD;
/**
 * 主命令
 * @author ks
 */
@MainCmd(mainCmd=MainCMD.USER_SOUL)
public class UserSoulHandler extends ActionAdapter{
	/**
	 * 强化战魂
	 * @param handler
	 * @param userSoulId
	 * @param soulIds
	 */
	@SubCmd(subCmd=UserSoulCMD.STRENG_USER_SOUL,args={"long","long_true"})
	public void strengUserSoul(GameHandler handler,long userSoulId,List<Long> soulIds){
		Application.sendMessage(handler.getChannel(), 
				handler.getHead(), 
				userSoulAction().strengUserSoul(handler.getPlayer().getUserId(), userSoulId, soulIds));
	}
	/**
	 * 进化战魂
	 * @param handler
	 * @param userSoulId
	 * @param soulIds
	 */
	@SubCmd(subCmd=UserSoulCMD.SOUL_EVOLUTION,args={"long","long_true"})
	public void soulEvolution(GameHandler handler,long userSoulId,List<Long> soulIds){
		Application.sendMessage(handler.getChannel(), 
				handler.getHead(), 
				userSoulAction().soulEvolution(handler.getPlayer().getUserId(), userSoulId, soulIds));
	}
	
	/**
	 * 卖出战魂
	 * @param handler
	 * @param userSoulIds 要卖出的战魂
	 */
	@SubCmd(subCmd=UserSoulCMD.SELL_SOUL,args={"long_true"})
	public void sellSoul(GameHandler handler,List<Long> userSoulIds){
		Application.sendMessage(handler.getChannel(), 
				handler.getHead(), 
				userSoulAction().sellSoul(handler.getPlayer().getUserId(), userSoulIds));
	}
	/**
	 * 召唤战魂
	 * @param handler
	 * @param type 召唤类型
	 */
	@SubCmd(subCmd=UserSoulCMD.CALL_SOUL,args={"int","int"})
	public void callSoul(GameHandler handler,int type,int num){
		Application.sendMessage(handler.getChannel(), 
				handler.getHead(), 
				userSoulAction().callSoul(handler.getPlayer().getUserId(),type,num));
	}
	/**
	 * 获得用户战魂信息
	 * @param handler
	 * @param userId 用户编号
	 */
	@SubCmd(subCmd=UserSoulCMD.GAIN_SOUL_INFO,args={"int"})
	public void gainUserSoulInfo(GameHandler handler,int userId){
		Application.sendMessage(handler.getChannel(), 
				handler.getHead(), 
				userSoulAction().gainUserSoulInfo(userId));
	}
	/**
	 * 增加战魂仓库容量
	 * @param handler
	 */
	@SubCmd(subCmd=UserSoulCMD.ADD_SOUL_CAPACITY)
	public void addSoulCapacity(GameHandler handler){
		Application.sendMessage(handler.getChannel(), 
				handler.getHead(), 
				userSoulAction().addSoulCapacity(handler.getPlayer().getUserId()));
	}
	
	/**
	 * 重塑战魂
	 * @param userId
	 * @param userSoulIds
	 * @return
	 */
	@SubCmd(subCmd=UserSoulCMD.SOUL_重塑战魂,args={"long_true"})
	public void reShapeSoul(GameHandler handler,List<Long> userSoulIds){
		Application.sendMessage(handler.getChannel(), 
				handler.getHead(), 
				userSoulAction().reShapeSoul(handler.getPlayer().getUserId(),userSoulIds));
	}
	
	/**
	 * 设置战魂保护状态
	 * @param userId 用户编号
	 * @param userSoulId 战魂编号
	 * @param safe 是否为保护
	 */
	@SubCmd(subCmd=UserSoulCMD.UPDATE_SOUL_SAFE,args={"long","boolean"})
	public void updateSoulSafe(GameHandler handler,long userSoulId,boolean safe){
		userSoulAction().updateSoulSafe(handler.getPlayer().getUserId(), userSoulId, safe);
		Application.sendMessage(handler.getChannel(), handler.getHead());
	}
	
	/**
	 * 新手领取强化素材
	 * @param handler
	 * @param choose 素材战魂ID
	 */
	@SubCmd(subCmd=UserSoulCMD.SOUL_领取强化素材,args={"int"})
	public void getGuideStrangSoul(GameHandler handler,int choose){
		Application.sendMessage(handler.getChannel(), 
				handler.getHead(), 
				userSoulAction().getGuideStrengSoul(handler.getPlayer().getUserId(), choose));
	}
	
	/**
	 * 新手领取进化战魂
	 * @param handler
	 * @param userSoulId 材战魂ID
	 */
	@SubCmd(subCmd=UserSoulCMD.SOUL_新手进化战魂,args={"int"})
	public void guideSoulEvolution(GameHandler handler,int userSoulId){
		Application.sendMessage(handler.getChannel(), 
				handler.getHead(), 
				userSoulAction().guideSoulEvolution(handler.getPlayer().getUserId(), userSoulId));
	}
}
