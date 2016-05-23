/**
 * 
 */
package com.ks.account.task;

import java.util.concurrent.TimeUnit;

import com.ks.account.service.PayService;
import com.ks.account.service.ServiceFactory;
import com.ks.timer.task.BaseTask;
import com.ks.timer.task.Task;

/**
 * @author living.li
 * @date 2015年5月19日 下午3:45:40
 * 
 * 
 */
@Task(initialDelay = 1, period = 3, unit = TimeUnit.MINUTES)
public class CacheReloadTask extends BaseTask {
	@Override
	public void runTask() {
		PayService service = ServiceFactory.getService(PayService.class);
		service.retryNofiGameServer();
	}
}
