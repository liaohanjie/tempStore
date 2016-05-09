package com.ks.action.world;

import java.util.List;

import com.ks.rpc.Async;

/**
 * 资源锁
 * @author zck
 *
 */
public interface LockAction {
	
	long lockUserKey(int partner, String username);
	
	/**
	 * 获得资源锁
	 * @param keys
	 * @return
	 */
	long lockKey(List<String> keys);
	
	/**
	 * 解锁资源
	 * @param lockId
	 * @return
	 */
	@Async
	void unlockKey(long lockId);
}
