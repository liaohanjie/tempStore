package com.ks.event;


import org.apache.log4j.Logger;

import com.ks.logger.LoggerFactory;

/**
 * 游戏事件可异步操作
 * @author ks.wu
 *
 */
public abstract class GameEvent implements Runnable {
	private static final Logger logger = LoggerFactory.get(GameEvent.class);
	
	protected boolean sync;
	/**
	 * 执行event
	 */
	public abstract void runEvent() throws Exception;
	
	@Override
	public final void run() {
		try{
			runEvent();
		}catch(Exception e){
			logger.error("",e);
		}
	}

	public boolean isSync() {
		return sync;
	}
	
}                                                  