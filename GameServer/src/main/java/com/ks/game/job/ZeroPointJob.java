package com.ks.game.job;

import com.ks.game.event.ZeroPointEvent;
import com.ks.game.model.Player;
import com.ks.manager.PlayerManager;
import com.ks.timer.TimerController;
import com.ks.timer.job.BaseJob;
import com.ks.timer.job.Job;

/**
 * 零点JOB
 * @author ks.wu
 *
 */
@Job(context="00:00?*")
public class ZeroPointJob extends BaseJob {

	@Override
	public void runJob() {
		for(Player player : PlayerManager.getAllOnlinePlayer().values()){
			ZeroPointEvent event = new ZeroPointEvent(player);
			TimerController.execEvent(event);
		}
	}

}
