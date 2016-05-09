package com.ks.manager;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

import com.ks.game.model.Player;

public class PlayerManager {
	
	/**在线玩家 key=sessionId*/
	private static Map<Long,Player> onlinePlayers = new ConcurrentHashMap<>();
	
	/**
	 * 增加在线玩家
	 * @param sessionId 会话编号
	 * @param player 玩家
	 */
	public static void addOnlinePlayer(Long sessionId,Player player){
		player.setLock(new ReentrantLock());
		onlinePlayers.put(sessionId, player);
	}
	
	/**
	 * 获取在线玩家
	 * @param sessionId 会话编号
	 * @return 在线玩家
	 */
	public static Player getOnlinePlayer(Long sessionId){
		return onlinePlayers.get(sessionId);
	}
	/**
	 * 删除在线玩家
	 * @param sessionId 会话编号
	 */
	public static void removeOnlinePlayer(Long sessionId){
		onlinePlayers.remove(sessionId);
	}
	
	public static Map<Long,Player> getAllOnlinePlayer(){
		return onlinePlayers;
	}
}
