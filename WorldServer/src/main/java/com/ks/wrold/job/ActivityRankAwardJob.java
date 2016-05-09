package com.ks.wrold.job;

import org.apache.log4j.Logger;

import com.ks.action.logic.ActivityAction;
import com.ks.app.Application;
import com.ks.logger.LoggerFactory;
import com.ks.rpc.RPCKernel;
import com.ks.timer.job.BaseJob;
import com.ks.timer.job.Job;


/**
 * 开服冲榜结束发放礼包, 活动结束后，在 00:00 执行发送礼包任务
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2015年7月17日
 */
@Job(context="00:003?*")
public class ActivityRankAwardJob extends BaseJob {
	
	private static final Logger logger = LoggerFactory.get(ActivityRankAwardJob.class);

	@Override
	public void runJob() {
		ActivityAction action = RPCKernel.getRemoteByServerType(Application.LOGIC_SERVER, ActivityAction.class);
		action.issueActivityRankAward();
		logger.info("冲榜礼包发送");
	}
}