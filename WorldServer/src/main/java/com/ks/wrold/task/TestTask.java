package com.ks.wrold.task;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.ks.action.game.GameServerAction;
import com.ks.rpc.RPCKernel;
import com.ks.timer.task.BaseTask;
import com.ks.timer.task.Task;
import com.ks.wrold.action.WorldServerActionImpl;
import com.ks.wrold.kernel.WorldServerCache;
@Task(initialDelay=10,period=10,unit=TimeUnit.SECONDS)
public class TestTask extends BaseTask {

	@Override
	public void runTask() {
		
		List<String> remServer = new ArrayList<>();
		
		for(com.ks.rpc.ServerInfo info : WorldServerCache.gainAllGameServer()){
			GameServerAction action = RPCKernel.getRemoteByServerId(info.getServerId(), GameServerAction.class);
			
			int fald = 0;
			while(true){
				try{
					action.ping();
					break;
				}catch(Exception e){
					fald++;
					if(fald>2){
						remServer.add(info.getServerId());
						new WorldServerActionImpl().clearGameServerPlayers(info.getServerId());
						break;
					}
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				}
			}
		}
		
		for(String serverId : remServer){
			WorldServerCache.removeGameServerInfo(serverId);
		}
	}
}
