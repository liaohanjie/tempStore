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
 * 苹果保存收据失败订单处理
 * @author lipp
 * 2015年11月13日
 */
@Task(initialDelay = 1, period = 3, unit = TimeUnit.MINUTES)
public class IapNotifyReloadTask extends BaseTask {
	@Override
	public void runTask() {
		PayService service = ServiceFactory.getService(PayService.class);
		service.reloadIAPNotify();
	}
}
