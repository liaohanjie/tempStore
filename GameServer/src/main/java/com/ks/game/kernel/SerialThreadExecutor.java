package com.ks.game.kernel;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import org.apache.log4j.Logger;
import com.ks.logger.LoggerFactory;

/**
 * 有序线程池
 * 
 * @author hanjie.l
 * 
 */
public class SerialThreadExecutor {
	private final Logger LOGGER = LoggerFactory.get(SerialThreadExecutor.class);

	/**
	 * 工作线程池
	 */
	private Executor executor;

	/**
	 * 有序任务队列
	 */
	private ConcurrentMap<Object, SequentialJob> serialJobs = new ConcurrentHashMap<Object, SequentialJob>();

	/**
	 * 互斥同步锁
	 */
	private final ReentrantLock takeLock = new ReentrantLock();

	private final Condition condition = this.takeLock.newCondition();

	private int blockTime = 15;

	private static SerialThreadExecutor instance = new SerialThreadExecutor(Executors.newScheduledThreadPool(2, new SerialThreadFactory()));

	public static SerialThreadExecutor getInstance() {
		return instance;
	}

	private SerialThreadExecutor(Executor executor) {
		this.executor = executor;
		Thread demonThead = new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {

					if (blockTime > 0) {
						try {
							takeLock.lockInterruptibly();
							condition.await(blockTime, TimeUnit.SECONDS);
						} catch (Exception e) {
							LOGGER.error("{}", e);
						} finally {
							takeLock.unlock();
						}
					}

					synchronized (this) {
						for (Map.Entry<Object, SequentialJob> job : serialJobs.entrySet()) {
							SequentialJob sjJob = job.getValue();

							if (sjJob.jobs.isEmpty()) {
								serialJobs.remove(job.getKey());
							}
						}
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
		job.addJob(r);
	}

	private class SequentialJob implements Runnable {
		private BlockingQueue<Runnable> jobs = new LinkedBlockingQueue<Runnable>();
		private Object key;
		private AtomicBoolean running = new AtomicBoolean(false);

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
						LOGGER.error(e);
					}
				} else {
					synchronized (this) {
						if (jobs.isEmpty() && running.compareAndSet(true, false)) {
							return;
						} else {
							continue;
						}
					}
				}
			}
		}

		public void addJob(Runnable r) {
			synchronized (this) {
				jobs.add(r);
				if (running.compareAndSet(false, true)) {
					executor.execute(this);
				}
			}
		}
	}
	
	private static class SerialThreadFactory implements ThreadFactory {

		@Override
		public Thread newThread(Runnable r) {
			return new Thread(r, "SerialThreadExecutor Thread");
		}

	}
}