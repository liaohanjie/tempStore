package com.ks.game.schedule;

import org.apache.log4j.Logger;
import com.ks.action.logic.SwapArenaAction;
import com.ks.app.Application;
import com.ks.logger.LoggerFactory;
import com.ks.rpc.RPCKernel;
import com.ks.schedue.ScheduledTask;
import com.ks.schedue.annotion.Scheduled;
/**
 * 交换竞技场发放奖励(每天凌晨0点执行)
 * @author hanjie.l
 *
 */
@Scheduled(name="交换竞技场发放奖励", value="0 0 22 * * *")
public class SwapArenaScheduling implements ScheduledTask{

	private final Logger LOGGER = LoggerFactory.get(SwapArenaScheduling.class);
	@Override
	public void runTask() {
		try {
			SwapArenaAction swapArenaAction = RPCKernel.getRemoteByServerType(Application.LOGIC_SERVER, SwapArenaAction.class);
			swapArenaAction.rewardTopPlayer();
		} catch (Exception e) {
			LOGGER.error(e);
		}
	}
}
