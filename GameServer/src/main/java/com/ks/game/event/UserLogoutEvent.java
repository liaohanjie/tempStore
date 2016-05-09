package com.ks.game.event;

import org.apache.log4j.Logger;

import com.ks.action.logic.PlayerAction;
import com.ks.action.world.LoginAction;
import com.ks.app.Application;
import com.ks.event.GameEvent;
import com.ks.game.model.Player;
import com.ks.logger.LoggerFactory;
import com.ks.manager.PlayerManager;
import com.ks.rpc.RPCKernel;
import com.ks.timer.TimerController;
/**
 * 用户登出事件
 * @author ks
 */
public class UserLogoutEvent extends GameEvent {
	
	private static final Logger logger = LoggerFactory.get(UserLogoutEvent.class);
	
	private Player player;
	private int count;
	public UserLogoutEvent(Player player) {
		super();
		this.player = player;
	}
	@Override
	public void runEvent() {
		player.lock();
		try{
			if(PlayerManager.getOnlinePlayer(player.getSessionId())==null){
				return;
			}
			
			PlayerAction playerAction = RPCKernel.getRemoteByServerType(Application.LOGIC_SERVER, PlayerAction.class);
			playerAction.logout(player.getUserId());
			
			LoginAction action = RPCKernel.getRemoteByServerType(Application.WORLD_SERVER, LoginAction.class);
			action.logout(player);
			
			PlayerManager.removeOnlinePlayer(player.getSessionId());
		}catch(Exception e){
			if(count<5){
				count++;
				TimerController.execEvent(this);
			}
			logger.error("logout error",e);
		}finally{
			player.unlock();
		}
	}

}
