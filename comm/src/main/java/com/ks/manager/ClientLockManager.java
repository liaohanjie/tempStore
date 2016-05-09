package com.ks.manager;

import java.util.ArrayList;
import java.util.List;

import com.ks.action.world.LockAction;

public class ClientLockManager {
	
	private static ThreadLocal<Long> localLocks = new ThreadLocal<Long>();
	private static LockAction lockAction;
	
	public static void init(LockAction lockAction){
		ClientLockManager.lockAction = lockAction;
	}
	
	public static void removeLocalLock(){
		Long lockId = localLocks.get();
		if(lockId != null){
			lockAction.unlockKey(lockId);
			localLocks.remove();
		}
	}
	
	public static void lock(List<String> keys){
		long lockId = lockAction.lockKey(keys);
		localLocks.set(lockId);
	}
	
	public static void lock(String key){
		List<String> keys = new ArrayList<String>();
		keys.add(key);
		lock(keys);
	}
	
	public static void unlockThreadLock(){
		removeLocalLock();
	}
	
	public static void lockUser(String username, int partner){
		long lockId = lockAction.lockUserKey(partner, username);
		localLocks.set(lockId);
	}
}
