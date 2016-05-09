package com.ks.game.handler;

import com.ks.app.Application;
import com.ks.handler.GameHandler;
import com.ks.protocol.MainCmd;
import com.ks.protocol.SubCmd;
import com.ks.protocol.main.MainCMD;
import com.ks.protocol.sub.RankCMD;
import com.ks.protocol.vo.rank.RankInfoVO;
/**
 * 排行榜
 * @author hanjie.l
 *
 */
@MainCmd(mainCmd = MainCMD.Rank)
public class RankHandler extends ActionAdapter{
	
	/**
	 * 获取公告
	 * @param handler
	 */
	@SubCmd(subCmd = RankCMD.getRank_获取排行榜, args = {"int"})
	public void getRankInfo(GameHandler handler, int rankTypeId) {
		RankInfoVO rankInfo = rankAction().getRankInfo(handler.getPlayer().getUserId(), rankTypeId);
		Application.sendMessage(handler.getChannel(), handler.getHead(), rankInfo);
	}

}
