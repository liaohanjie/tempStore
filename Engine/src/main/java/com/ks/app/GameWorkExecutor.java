package com.ks.app;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 游戏工作线程池
 * @author ks
 */
public final class GameWorkExecutor {
	/**工作线程池*/
	private static ThreadPoolExecutor e= new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors()*2, 
			Runtime.getRuntime().availableProcessors()*2, 
			5, TimeUnit.SECONDS, 
			new LinkedBlockingQueue<Runnable>(20000));
	/**
	 * 初始化
	 * @param threadPoolSize
	 */
	public static void initGameWorkExecutor(int threadPoolSize){
		e.shutdownNow();
		e = new ThreadPoolExecutor(threadPoolSize, 
				threadPoolSize, 
				5, TimeUnit.SECONDS, 
				new LinkedBlockingQueue<Runnable>(20000));
	}
	/**
	 * 执行
	 * @param command 命令
	 */
	public static void execute(Runnable command){
		e.execute(command);
	}
}