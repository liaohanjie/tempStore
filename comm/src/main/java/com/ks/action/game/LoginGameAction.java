package com.ks.action.game;

import com.ks.game.model.Player;

/**
 * 登录游戏
 * @author ks
 *
 */
public interface LoginGameAction {
	/**
	 * 重复登录
	 * @param sessionId 会话编号
	 */
	void repeatLogin(long sessionId);
	
	/**
	 * 增加在线玩家
	 * @param sessionId 会话编号
	 * @param player 在线玩家
	 */
	void addOnlinePlayer(long sessionId,Player player);
}