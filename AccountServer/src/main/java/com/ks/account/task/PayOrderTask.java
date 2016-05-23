/**
 * 
 */
package com.ks.account.task;

import java.util.concurrent.TimeUnit;

import com.ks.account.cache.AccountCache;
import com.ks.timer.task.BaseTask;
import com.ks.timer.task.Task;

/**
 * @author living.li
 * @date 2015年5月19日 下午3:45:40
 * 
 * 
 */
@Task(initialDelay = 5, period = 5, unit = TimeUnit.MINUTES)
public class PayOrderTask extends BaseTask {
	@Override
	public void runTask() {
		AccountCache.reload();
	}
}
