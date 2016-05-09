package com.ks.lock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.log4j.Logger;

import com.ks.logger.LoggerFactory;

/**
 * 键值锁管理
 * @author zck
 *
 */
public class KeyLockManager {
	private static final Logger logger = LoggerFactory.get(KeyLockManager.class);

	/**默认10秒过期*/
	public long timeoutTime = 10*1000;
	/**键值*/
	private List<String> keyCache = new ArrayList<String>();
	/**锁*/
	private Map<Long, KeyLock> lockCache = new HashMap<Long, KeyLock>();
	/**锁id产生器*/
	private AtomicLong lockIdCreator = new AtomicLong();
	/**锁超时扫描器*/
	private KeyLockScanThread scanThread;
	
	public KeyLockManager(long timeoutTime) throws Exception{
		this.timeoutTime = timeoutTime;
		initScanThread();
	}
	
	private void initScanThread(){
		scanThread = new KeyLockScanThread(this);
		scanThread.start();
	}
	
	/**
	 * 锁定键值
	 * @param keys
	 * @return
	 */
	public KeyLock lockKey(String... keys){
		List<String> list = new ArrayList<String>();
		for(String key : keys){
			list.add(key);
		}
		return lockKey(list);
	}
	
	/**
	 * 锁定键值
	 * @param keys
	 * @return
	 */
	public KeyLock lockKey(List<String> keys){
		Collections.sort(keys);
		boolean sign = true;
		while(sign){
			sign = false;
			synchronized (keyCache) {
				for(String k : keys){
					if(keyCache.contains(k)){
						sign = true;
						break;
					}
				}
				if(!sign){
					keyCache.addAll(keys);
					KeyLock lock = new KeyLock(lockIdCreator.incrementAndGet(), keys);
					lockCache.put(lock.getId(), lock);
					lock.lock(timeoutTime);
					return lock;
				}
			}
			if(sign){
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	
	/**
	 * 解锁
	 * @param lockId
	 */
	public void unlockKey(long lockId){
		synchronized (keyCache) {
			KeyLock lock = lockCache.get(lockId);
			unlockKey(lock);
		}
	}
	/**
	 * 解锁
	 * @param lock
	 */
	public void unlockKey(KeyLock lock){
		if(lock != null){
			synchronized (keyCache) {
				List<String> keys = lock.getKeys();
				keyCache.removeAll(keys);
				lockCache.remove(lock.getId());
			}
		}
	}
	
	/**
	 * 锁超时扫描
	 * @author zck
	 *
	 */
	static class KeyLockScanThread extends Thread{
		KeyLockManager manager;
		public KeyLockScanThread(KeyLockManager manager){
			this.manager = manager;
		}
		@Override
		public void run() {
			super.run();
			while(true){
				try {
					synchronized (manager.keyCache) {
						long now = System.currentTimeMillis();
						for(KeyLock lock : manager.lockCache.values()){
							if(lock.isTimeout(now)){
								manager.unlockKey(lock);
								logger.warn("lock[" + lock.getId() + "] time out!");
							}
						}
					}
					sleep(1000);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
	}
}
