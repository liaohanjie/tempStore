package com.ks.game.kernel;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 有序线程池
 * 
 * @author hanjie.l
 * 
 */
public class SerialThreadExecutor {

	/**
	 * 核心线程数
	 */
	private static final int corePoolSize = 5;

	/**
	 * 工作线程池
	 */
	private Executor executor;

	/**
	 * 有序任务队列
	 */
	private ConcurrentMap<Object, SequentialJob> serialJobs = new ConcurrentHashMap<Object, SequentialJob>();

	/**
	 * 清理空任务队列间隔
	 */
	private int blockTime = 15;

	/**
	 * 初始化单例
	 */
	private static SerialThreadExecutor instance = new SerialThreadExecutor(Executors.newScheduledThreadPool(corePoolSize, new SerialThreadFactory()));

	/**
	 * 获取实例
	 * 
	 * @return
	 */
	public static SerialThreadExecutor getInstance() {
		return instance;
	}

	private SerialThreadExecutor(Executor executor) {
		this.executor = executor;

		// 启动清理空任务队列的线程
		Thread demonThead = new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					try {
						Thread.sleep(blockTime * 1000);

						for (Map.Entry<Object, SequentialJob> job : serialJobs.entrySet()) {
							SequentialJob sjJob = job.getValue();
							// 假如队列为空，并且没有正在执行的任务则移除
							if (sjJob.jobs.isEmpty() && sjJob.running.get() == false) {
								// 保证移除的时候一定是空的队列
								sjJob.lock.writeLock().lock();
								try {
									// 再次进行判断
									if (sjJob.jobs.isEmpty() && sjJob.running.get() == false) {
										serialJobs.remove(job.getKey());
									}
								} finally {
									sjJob.lock.writeLock().unlock();
								}
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			}
		});
		demonThead.setName("SerialThreadExecutor maintain Thread");
		demonThead.setDaemon(true);
		demonThead.start();
	}

	/**
	 * 以key为队列标记有序执行一系列任务
	 * 
	 * @param key
	 * @param r
	 */
	public void executeSerially(Object key, Runnable r) {
		SequentialJob job = serialJobs.get(key);
		if (job == null) {
			job = new SequentialJob(key);
			SequentialJob oldJob = serialJobs.putIfAbsent(job.getKey(), job);
			if (oldJob != null) {
				job = oldJob;
			}
		}

		// 这里加锁是为了防止和清理空任务队列的线程产生并发，SequentialJob里面的queue本身是线程安全的，所以只是用读锁
		job.lock.readLock().lock();
		try {
			// 保证没有被移除,否则重新提交任务
			if (serialJobs.containsValue(job)) {
				job.addJob(r);
			} else {
				executeSerially(key, r);
			}
		} finally {
			job.lock.readLock().unlock();
		}

	}

	/**
	 * 任务队列
	 * 
	 * @author hanjie.l
	 * 
	 */
	private class SequentialJob implements Runnable {

		/**
		 * 队列的身份标识key
		 */
		private Object key;

		/**
		 * 任务队列
		 */
		private BlockingQueue<Runnable> jobs = new LinkedBlockingQueue<Runnable>();

		/**
		 * 标记队列任务是否在执行的原子类
		 */
		private AtomicBoolean running = new AtomicBoolean(false);

		/**
		 * 互斥同步锁
		 */
		private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

		public SequentialJob(Object key) {
			this.key = key;
		}

		public Object getKey() {
			return key;
		}

		public void run() {
			Runnable r = null;
			while (true) {
				r = jobs.poll();
				if (r != null) {
					try {
						r.run();
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					if (jobs.isEmpty() && running.compareAndSet(true, false)) {
						// 刚才队列为空不代表现在也是没有，在running设置成false过程中还是有加入新任务的可能，所以要重新判断
						if (!jobs.isEmpty()) {
							// 假如不为空，则继续把自己扔回线程池处理，但是有可能addjob中已经扔了一次，所以要利用running判断好
							if (running.compareAndSet(false, true)) {
								executor.execute(this);
							}
						} else {
							return;
						}
					}
				}
			}
		}

		/**
		 * 往队列中加入任务，如果任务队列处于停止运行状态则启动
		 * 
		 * @param r
		 */
		public void addJob(Runnable r) {
			jobs.add(r);
			// 假如没有执行则执行
			if (running.compareAndSet(false, true)) {
				executor.execute(this);
			}
		}
	}

	/**
	 * 线程工厂
	 * 
	 * @author Administrator
	 * 
	 */
	private static class SerialThreadFactory implements ThreadFactory {

		private static AtomicInteger idGenerater = new AtomicInteger(0);

		@Override
		public Thread newThread(Runnable r) {
			return new Thread(r, "SerialThreadExecutor Thread" + idGenerater.incrementAndGet());
		}
	}
}