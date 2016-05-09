package com.ks.game.handler;

import com.ks.action.logic.NoticeAction;
import com.ks.app.Application;
import com.ks.handler.GameHandler;
import com.ks.protocol.MainCmd;
import com.ks.protocol.SubCmd;
import com.ks.protocol.main.MainCMD;
import com.ks.protocol.sub.NoticeCMD;
import com.ks.rpc.RPCKernel;

@MainCmd(mainCmd = MainCMD.NOTICE)
public class NoticeHandler extends ActionAdapter{
	
	protected final static NoticeAction action(){
		return RPCKernel.getRemoteByServerType(Application.LOGIC_SERVER, NoticeAction.class);
	}
	
	/**
	 * 拉取公告
	 * 
	 * @param handler
	 */
	@SubCmd(subCmd = NoticeCMD.POLL)
	public void poll(GameHandler handler) {
		Application.sendMessage(handler.getChannel(), handler.getHead(), action().poll(handler.getPlayer().getUserId()));
	}
}
