package com.ks.game.handler;

import com.ks.app.Application;
import com.ks.handler.GameHandler;
import com.ks.protocol.MainCmd;
import com.ks.protocol.SubCmd;
import com.ks.protocol.main.MainCMD;
import com.ks.protocol.sub.UserSoulMapCMD;

@MainCmd(mainCmd=MainCMD.USER_SOUL_MAP)
public class UserSoulMapHanlder extends ActionAdapter {

	/**
	 * 查看我的图鉴
	 * @param handler
	 */
	@SubCmd(subCmd=UserSoulMapCMD.VIEW_MY_SOUL_MAP)
	public void queryUserSoulMap(GameHandler handler){
		Application.sendMessage(handler.getChannel(), 
				handler.getHead(), 
				userSoulAction().queryUserSoulMap(handler.getPlayer().getUserId()));
	}
}
