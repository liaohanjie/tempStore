package com.ks.game.schedule;

import java.util.List;

import org.apache.log4j.Logger;

import com.ks.action.logic.BossAction;
import com.ks.app.Application;
import com.ks.game.kernel.SerialThreadExecutor;
import com.ks.logger.LoggerFactory;
import com.ks.model.boss.CheckBossOpenResult;
import com.ks.rpc.RPCKernel;
import com.ks.schedue.ScheduledTask;
import com.ks.schedue.annotion.Scheduled;
/**
 * boss状态检测(每分钟执行一次)
 * @author hanjie.l
 *
 */
@Scheduled(name="boss状态检测", value="0 * * * * *")
public class BossActivityScheduling implements ScheduledTask{
	
	private final Logger LOGGER = LoggerFactory.get(BossActivityScheduling.class);

	@Override
	public void runTask() {
		final BossAction bossAction = RPCKernel.getRemoteByServerType(Application.LOGIC_SERVER, BossAction.class);
		List<CheckBossOpenResult> checkBossOpenResult = bossAction.getCheckBossOpenResult();
		for(int i=0; i<checkBossOpenResult.size(); i++){
			final CheckBossOpenResult result = checkBossOpenResult.get(i);
			try {
				//开启
				if(result.isOpen()){
					SerialThreadExecutor.getInstance().executeSerially(result.getBossId(), new Runnable() {
						public void run() {
							bossAction.initWorldBoss(result.getBossId(), result.getVersion(), result.getEndTime());
						}
					});
				//关闭
				}else{
					SerialThreadExecutor.getInstance().executeSerially(result.getBossId(), new Runnable() {
						public void run() {
							bossAction.destroyWorldBoss(result.getBossId(), result.getNextOpenTime());
						}
					});
				}
			} catch (Exception e) {
				LOGGER.error(e);
			}
		}
		
	}

}
