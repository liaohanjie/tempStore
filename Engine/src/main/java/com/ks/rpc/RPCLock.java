package com.ks.rpc;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 远程调用锁
 * @author ks
 */
public final class RPCLock {
	/**远程调用锁*/
	private Lock lock;
	/**远程调用锁条件*/
	private Condition condition;
	public RPCLock(){
		lock = new ReentrantLock();
		condition = lock.newCondition();
	}
	public void lock(){
		lock.lock();
	}
	public void unlock(){
		lock.unlock();
	}
	public void await() throws InterruptedException{
		condition.await();
	}
	public boolean await(long time, TimeUnit unit) throws InterruptedException{
		return condition.await(time,unit);
	}
	public void signal(){
		condition.signal();
	}
}
