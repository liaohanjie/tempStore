package com.ks.game.event;

import com.ks.action.logic.PlayerAction;
import com.ks.app.Application;
import com.ks.event.GameEvent;
import com.ks.game.model.Player;
import com.ks.rpc.RPCKernel;

public class ZeroPointEvent extends GameEvent {
	
	private Player player;
	public ZeroPointEvent(Player player) {
		this.player = player;
	}
	@Override
	public void runEvent() {
		player.lock();
		try{
			PlayerAction action = RPCKernel.getRemoteByServerType(Application.LOGIC_SERVER, PlayerAction.class);
			action.zeroResetUserStat(player.getUserId());
		}finally{
			player.unlock();
		}
	}

}
