package com.ks.game.handler;

import com.ks.action.logic.StatAction;
import com.ks.app.Application;
import com.ks.handler.GameHandler;
import com.ks.protocol.MainCmd;
import com.ks.protocol.SubCmd;
import com.ks.protocol.main.MainCMD;
import com.ks.protocol.sub.GlobalCMD;
import com.ks.rpc.RPCKernel;

@MainCmd(mainCmd=MainCMD.GLOBAL)
public class GlobalHandler extends ActionAdapter {
	
	/**
	 * 获取游戏状态
	 * @param handler
	 * @param int
	 */
	@SubCmd(subCmd=GlobalCMD.GET_STAT, args={"int"})
	public void getStat(GameHandler handler, int id) {
		StatAction action = RPCKernel.getRemoteByServerType(Application.LOGIC_SERVER, StatAction.class);
		Application.sendMessage(handler.getChannel(), handler.getHead(), action.findById(id));
	}
	
}
