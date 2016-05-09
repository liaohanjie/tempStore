package com.ks.lock;

import java.util.ArrayList;
import java.util.List;

public class KeyLock {
	private long id;
	/**键值*/
	private List<String> keys = new ArrayList<String>();
	/**锁定时间*/
	private long lockTime;
	/**过期时间*/
	private long expiredTime;
	
	public KeyLock(long id, List<String> keys) {
		super();
		this.id = id;
		this.keys = keys;
	}

	public void lock(long timeout_time){
		lockTime = System.currentTimeMillis();
		expiredTime = lockTime + timeout_time;
	}
	
	public boolean isTimeout(long now){
		return expiredTime <= now;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	public List<String> getKeys() {
		return keys;
	}
	public void setKeys(List<String> keys) {
		this.keys = keys;
	}
	public long getLockTime() {
		return lockTime;
	}
	public void setLockTime(long lockTime) {
		this.lockTime = lockTime;
	}
	
}
