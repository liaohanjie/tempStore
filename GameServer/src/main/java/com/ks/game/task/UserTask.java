package com.ks.game.task;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.ks.event.GameEvent;
import com.ks.game.event.UserLogoutEvent;
import com.ks.game.model.Player;
import com.ks.manager.PlayerManager;
import com.ks.timer.TimerController;
import com.ks.timer.task.BaseTask;
import com.ks.timer.task.Task;
/**
 * 用户task
 * @author ks
 */
@Task(initialDelay=5,period=5,unit=TimeUnit.MINUTES)
public class UserTask extends BaseTask {
	
	@Override
	public void runTask() {
		List<Player> players = new ArrayList<>();
		for(Player player : PlayerManager.getAllOnlinePlayer().values()){
			if(player.getLastHeartTime()==null){
				player.setLastHeartTime(new Date());
			}
			if((new Date().getTime()-player.getLastHeartTime().getTime())>Player.LOGOUT_TIME&&
					(new Date().getTime()-player.getLastMessageTime().getTime())>Player.LOGOUT_TIME){
				players.add(player);
			}
		}
		for(Player player : players){
			GameEvent event = new UserLogoutEvent(player);
			TimerController.execEvent(event);
		}
	}
}
