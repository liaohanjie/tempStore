/**
 * 
 */
package com.ks.game.handler;

import java.util.List;

import com.ks.app.Application;
import com.ks.handler.GameHandler;
import com.ks.protocol.MainCmd;
import com.ks.protocol.SubCmd;
import com.ks.protocol.main.MainCMD;
import com.ks.protocol.vo.achieve.AchieveAwardVO;
import com.ks.protocol.vo.achieve.UserAchieveVO;

/**
 * @author living.li
 * @date  2014年4月26日
 * 用户成就
 * USER_ACHIEVE= 12
 */
@MainCmd(mainCmd=MainCMD.USER_用户成就)
public class UserAchieveHanlder extends ActionAdapter{
	
	/**用户成就*/
	private static final int subCmd_查看用户成就=1;
	/**用户成就奖励*/
	public static final int subCmd_用户成就奖励=2;
	

	/**
	 * 查看我的成就
	 * @param userId
	 * @return
	 */
	@SubCmd(subCmd=subCmd_查看用户成就)
	public void getUserAchieve(GameHandler handler){
		List<UserAchieveVO> list=userAchieveAction().getUserAchieve(handler.getPlayer().getUserId());
		Application.sendMessage(handler.getChannel(), handler.getHead(), list);
	}
	/**
	 * 领取成就奖励
	 * @param userId
	 * @param achieveId
	 * @return AchieveAwardVO 
	 */
	@SubCmd(subCmd=subCmd_用户成就奖励,args={"int"})
	public void getAchieveAward(GameHandler handler,int achieveId){
		AchieveAwardVO vo=userAchieveAction().getAchieveAward(handler.getPlayer().getUserId(),achieveId);
		Application.sendMessage(handler.getChannel(), handler.getHead(), vo);
	}
	
	
}
