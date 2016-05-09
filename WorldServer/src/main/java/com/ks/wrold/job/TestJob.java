package com.ks.wrold.job;

import com.ks.timer.job.BaseJob;
import com.ks.timer.job.Job;
@Job(context="20:55,21:58,21:59?1,2,3")
public class TestJob extends BaseJob {

	@Override
	public void runJob() {
	}

}
