package com.ks.game.action;

import com.ks.action.game.GameNotifiAction;
import com.ks.game.model.Player;
import com.ks.manager.PlayerManager;

public class GameNotifiActionImpl implements GameNotifiAction {
	//private Logger logger=LoggerFactory.get(GameNotifiActionImpl.class);
	@Override
	public void notifiPlayer(long sessionId, int nofitiType) {
		Player player=PlayerManager.getOnlinePlayer(sessionId);
		if(player==null){
			return;
		}
		player.lock();
		try {
			player.setNofiti(player.getNofiti()|nofitiType);
		} finally{
			player.unlock();
		}
	}
}
