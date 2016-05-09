package com.ks.game.handler;
import com.ks.app.Application;
import com.ks.handler.GameHandler;
import com.ks.protocol.MainCmd;
import com.ks.protocol.SubCmd;
import com.ks.protocol.main.MainCMD;
import com.ks.protocol.sub.CheckFightCMD;
import com.ks.protocol.vo.check.CheckVO;
/**
 * 战斗校验
 * @author hanjie.l
 *
 */
@MainCmd(mainCmd = MainCMD.CHECKFIGHT)
public class FightCheckHandler extends ActionAdapter{
	
	/**
	 * 校验战斗信息
	 * @param handler
	 * @param checkVO
	 */
	@SubCmd(subCmd=CheckFightCMD.CHECK_FIGTHT, args={"check"})
	public void check(GameHandler handler, CheckVO checkVO){
		fightCheckAction().check(handler.getPlayer().getUserId(), checkVO);
		Application.sendMessage(handler.getChannel(), handler.getHead());
	}
	
	/**
	 * 上报可疑数据
	 * @param handler
	 * @param clientData
	 */
	@SubCmd(subCmd=CheckFightCMD.REPORT_DOUBTLOG)
	public void reportDoubtLog(GameHandler handler){
		fightCheckAction().reportDoubtLog(handler.getPlayer().getUserId(), "");
		Application.sendMessage(handler.getChannel(), handler.getHead());
	}
	

}
