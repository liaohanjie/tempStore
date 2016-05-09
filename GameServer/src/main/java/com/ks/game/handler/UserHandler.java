package com.ks.game.handler;

import java.util.Date;

import com.ks.app.Application;
import com.ks.game.model.Player;
import com.ks.handler.GameHandler;
import com.ks.protocol.MainCmd;
import com.ks.protocol.SubCmd;
import com.ks.protocol.main.MainCMD;
import com.ks.protocol.sub.UserCMD;
import com.ks.protocol.vo.Head;
import com.ks.protocol.vo.user.UserInfoVO;

/**
 * 用户
 * @author ks
 */
@MainCmd(mainCmd=MainCMD.USER)
public class UserHandler extends ActionAdapter{
	/**
	 * 获取用户信息
	 * @param handler
	 */
	@SubCmd(subCmd=UserCMD.GAIN_USER_INFO)
	public void gainUserInfo(GameHandler handler){
		Player player = handler.getPlayer();
		UserInfoVO userInfo = playerAction().gainUserInfo(player.getUserId());
		Head head = handler.getHead();
		Application.sendMessage(handler.getChannel(), head, userInfo);
	}
	/**
	 * 用户心跳
	 * @param handler
	 */
	@SubCmd(subCmd=UserCMD.USER_HEART)
	public void userHeart(GameHandler handler){
		Player player = handler.getPlayer();
		Date now = new Date();
		Date lastHeartTime = player.getLastHeartTime();
		if(lastHeartTime==null){
			lastHeartTime = new Date(now.getTime()-Player.HEART_FREQUENCY);
		}
//		long time = now.getTime() - lastHeartTime.getTime();
//		boolean reset = true;
//		if(time<(Player.HEART_FREQUENCY-30*1000L)){
//			reset = false;
//			player.setExceptionHeartCount(player.getExceptionHeartCount()+1);
//			if(player.getExceptionHeartCount()>2){
//				LoginAction action = RPCKernel.getRemoteByServerType(Application.WORLD_SERVER, LoginAction.class);
//				action.logout(player);
//				
//				PlayerAction playerAction = RPCKernel.getRemoteByServerType(Application.LOGIC_SERVER, PlayerAction.class);
//				playerAction.logout(player.getUserId());
//				
//				PlayerManager.removeOnlinePlayer(player.getSessionId());
//				
//				throw new GameException(GameException.CODE_心跳异常, "");
//			}
//		}
//		if(reset){
//			player.setExceptionHeartCount(0);
//		}
		player.setLastHeartTime(now);
		Head head = handler.getHead();
		Application.sendMessage(handler.getChannel(), head, now.getTime());
	}
	/**
	 * 获取用户统计
	 * @param handler
	 */
	@SubCmd(subCmd=UserCMD.GAIN_USER_STAT)
	public void gainUserStat(GameHandler handler){
		Player player = handler.getPlayer();
		Application.sendMessage(handler.getChannel(), handler.getHead(), playerAction().gainUserStat(player.getUserId()));
	}
	/**
	 * 恢复体力
	 * @param handler
	 */
	@SubCmd(subCmd=UserCMD.REGAIN_STAMINA)
	public void regainStamina(GameHandler handler){
		Player player = handler.getPlayer();
		Application.sendMessage(handler.getChannel(), handler.getHead(), playerAction().regainStamina(player.getUserId()));
	}
	
	/**
	 * 新手取名
	 * @param handler
	 * @param playerName
	 */
	@SubCmd(subCmd=UserCMD.USER_取名,args={"String"})
	public void givePlayerName(GameHandler handler,String playerName){
		Player player = handler.getPlayer();
		Application.sendMessage(handler.getChannel(), handler.getHead(), playerAction().givePlayerName(playerName,player.getUserId()));
	}
	
	/**
	 * 选择战魂
	 * @param handler
	 * @param soulId
	 */
	@SubCmd(subCmd=UserCMD.USER_选择战魂,args={"int"})
	public void guideChooseSoul(GameHandler handler,int soulId){
		Player player = handler.getPlayer();
		Application.sendMessage(handler.getChannel(), handler.getHead(), playerAction().newbieSoul(soulId,player.getUserId()));
	}
	/**
	 * 引导下一步
	 * @param handler
	 * @param nextStep
	 */
	@SubCmd(subCmd=UserCMD.USER_引导下一步,args={"int"})
	public void nextStepGuide(GameHandler handler,int nextStep){
		Player player = handler.getPlayer();
		Application.sendMessage(handler.getChannel(), handler.getHead(),playerAction().nextSetp(nextStep,player.getUserId()));
	}
	
	/**
	 * 恢复体力
	 * @param handler
	 */
	@SubCmd(subCmd=UserCMD.USER_用户_BUFF,args={})
	public void gainUserBuff(GameHandler handler){
		Player player = handler.getPlayer();
		Application.sendMessage(handler.getChannel(), handler.getHead(),playerAction().gainUserBuff(player.getUserId()));
	}
	
	/**
	 * 购买扫荡次数
	 * @param handler
	 * @param count
	 */
	@SubCmd(subCmd=UserCMD.USER_购买扫荡次数,args={"int"})
	public void buySweepCount(GameHandler handler,int count){
		Player player = handler.getPlayer();
		Application.sendMessage(handler.getChannel(), handler.getHead(), playerAction().buySweepCount(player.getUserId(),count));
	}
	
	/**
	 * 领取基金奖励
	 * @param handler
	 * @param grade
	 */
	@SubCmd(subCmd=UserCMD.USER_领取基金奖励,args={"int"})
	public void getGrowthCurrency(GameHandler handler,int grade){
		Player player = handler.getPlayer();
		int currency=playerAction().getGrowthCurrency(player.getUserId(), grade);
		Application.sendMessage(handler.getChannel(), handler.getHead(),currency);
	}
	
	/**
	 * 购买基金
	 * @param handler
	 */
	@SubCmd(subCmd=UserCMD.USER_购买基金)
	public void buyGrowthFund(GameHandler handler){
		Player player = handler.getPlayer();
		int currency=playerAction().buyGrowthfund(player.getUserId());
		Application.sendMessage(handler.getChannel(), handler.getHead(),currency);
	}
	/**
	 * 非强制引导下一步
	 * @param handler
	 * @param step
	 */
	@SubCmd(subCmd=UserCMD.USER_非强制引导下一步,args={"int"})
	public void nextInfoStep(GameHandler handler,int step){
		Player player = handler.getPlayer();
		playerAction().nextInfoStep(step, player.getUserId());
		Application.sendMessage(handler.getChannel(), handler.getHead());
	}
	
	/**
	 * 剧情副本
	 * @param handler
	 * @param nextStory
	 */
	@SubCmd(subCmd=UserCMD.USER_剧情副本,args={"int"})
	public void nextStoryMission(GameHandler handler,int nextStory){
		Player player = handler.getPlayer();
		playerAction().nextStoryMission(nextStory,player.getUserId());
		Application.sendMessage(handler.getChannel(), handler.getHead());
	}
	/**
	 * 免费领取体力
	 * @param handler
	 * 
	 */
	@SubCmd(subCmd=UserCMD.USER_SEND_STAMAIN)
	public void sendRegainStamina(GameHandler handler){
		Player player = handler.getPlayer();
		playerAction().sendRegainStamina(player.getUserId());
		Application.sendMessage(handler.getChannel(), handler.getHead());
	}
	
	/**
	 * 排行
	 * @param handler
	 * @param rankTypeId 排行类型编号
	 */
	@SubCmd(subCmd=UserCMD.USER_RANK, args = {"int"})
	public void userRank(GameHandler handler, int rankTypeId){
		Application.sendMessage(handler.getChannel(), handler.getHead(), playerAction().userRank(rankTypeId));
	}
	
	/**
	 * 点金手
	 * @param handler
	 */
	@SubCmd(subCmd=UserCMD.COIN_HAND)
	public void coinHand(GameHandler handler){
		Application.sendMessage(handler.getChannel(), handler.getHead(), playerAction().coinHand(handler.getPlayer().getUserId()));
	}
}
