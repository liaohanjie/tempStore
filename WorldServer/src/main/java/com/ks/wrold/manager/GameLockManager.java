package com.ks.wrold.manager;

import com.ks.action.logic.UserAction;
import com.ks.app.Application;
import com.ks.lock.KeyLock;
import com.ks.lock.KeyLockManager;
import com.ks.model.user.User;
import com.ks.rpc.RPCKernel;
import com.ks.util.LockKeyUtil;

public class GameLockManager extends KeyLockManager{
	private static GameLockManager instance;
	
	public GameLockManager(long timeoutTime) throws Exception {
		super(timeoutTime);
	}

	public static void init(long timeoutTime) throws Exception{
		if(instance == null){
			instance = new GameLockManager(timeoutTime);
		}
	}
	
	public static GameLockManager getInstance() {
		return instance;
	}
	
	public long lockUser(String username, int partner){
		UserAction userAction = RPCKernel.getRemoteByServerType(Application.LOGIC_SERVER, UserAction.class);
		User user = userAction.bossFindUserByUsername(username, partner);
		int userId = user == null? 0 : user.getUserId();
		if(userId != 0){
			KeyLock lock = this.lockKey(LockKeyUtil.getUserLockKey(userId));
			return lock.getId();
		}
		return 0;
	}
	
}
