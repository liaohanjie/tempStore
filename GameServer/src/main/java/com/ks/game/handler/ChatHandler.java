package com.ks.game.handler;

import com.ks.action.world.WorldChatAction;
import com.ks.app.Application;
import com.ks.handler.GameHandler;
import com.ks.protocol.MainCmd;
import com.ks.protocol.SubCmd;
import com.ks.protocol.main.MainCMD;
import com.ks.protocol.sub.ChatCMD;
import com.ks.protocol.vo.chat.ChatMsgRequestVO;
import com.ks.rpc.RPCKernel;

@MainCmd(mainCmd=MainCMD.CHAT)
public class ChatHandler extends ActionAdapter {
	
	/**
	 * 发送聊天信息
	 * @param handler
	 * @param chatMessageVO
	 */
	@SubCmd(subCmd=ChatCMD.SEND, args={"chatMsgRequest"})
	public void send(GameHandler handler, ChatMsgRequestVO chatMsgRequestVO) {
		WorldChatAction worldAction = RPCKernel.getRemoteByServerType(Application.WORLD_SERVER, WorldChatAction.class);
		worldAction.send(chatMsgRequestVO);
		Application.sendMessage(handler.getChannel(), handler.getHead());
	}
	
	/**
	 * 轮询聊天信息
	 * @param handler
	 * @param towerFloor
	 */
	@SubCmd(subCmd=ChatCMD.POLL)
	public void poll(GameHandler handler) {
		WorldChatAction worldAction = RPCKernel.getRemoteByServerType(Application.WORLD_SERVER, WorldChatAction.class);
		Application.sendMessage(handler.getChannel(), handler.getHead(), worldAction.poll(handler.getPlayer().getUserId()));
	}
	
	/**
	 * 轮询跑马灯信息
	 * @param handler
	 * @param towerFloor
	 */
	@SubCmd(subCmd=ChatCMD.MARQUEE)
	public void marquee(GameHandler handler) {
		WorldChatAction worldAction = RPCKernel.getRemoteByServerType(Application.WORLD_SERVER, WorldChatAction.class);
		Application.sendMessage(handler.getChannel(), handler.getHead(), worldAction.pollMarquee(handler.getPlayer().getUserId()));
	}
	
}
