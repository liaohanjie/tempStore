package com.ks.wrold.action;


import java.util.List;

import com.ks.action.world.LockAction;
import com.ks.lock.KeyLock;
import com.ks.wrold.manager.GameLockManager;

public class LockActionImpl implements LockAction {
	
	@Override
	public long lockUserKey(int partner, String username){
		return GameLockManager.getInstance().lockUser(username, partner);
	}
	
	@Override
	public long lockKey(List<String> keys) {
		KeyLock lock = GameLockManager.getInstance().lockKey(keys);
		return lock.getId();
	}
	
	@Override
	public void unlockKey(long lockId){
		GameLockManager.getInstance().unlockKey(lockId);
	}
}
