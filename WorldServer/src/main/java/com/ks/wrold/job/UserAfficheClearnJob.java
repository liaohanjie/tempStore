/**
 * 
 */
package com.ks.wrold.job;

import com.ks.action.logic.AfficheAction;
import com.ks.app.Application;
import com.ks.rpc.RPCKernel;
import com.ks.timer.job.BaseJob;
import com.ks.timer.job.Job;


/**
 * @author living.li
 * @date  2014年12月8日 下午3:31:35
 *	@Job(context="00:00?*") 
 *
 */
@Job(context="00:003?*")
public class UserAfficheClearnJob extends BaseJob {

	@Override
	public void runJob() {
		AfficheAction afficheAction = RPCKernel.getRemoteByServerType(Application.LOGIC_SERVER,AfficheAction.class);
		afficheAction.cleanAffiche();
	}

}