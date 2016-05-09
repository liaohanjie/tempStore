package com.ks.wrold.kernel;

import java.sql.Timestamp;

import org.apache.log4j.Logger;

import com.ks.app.Application;
import com.ks.logger.LoggerFactory;
import com.ks.rpc.server.HttpServerBootStrap;
import com.ks.rpc.server.RPCServerBootstrap;
import com.ks.wrold.manager.GameLockManager;

public final class WorldServerKernel {
	private static final Logger logger = LoggerFactory.get(WorldServerKernel.class);
	private static void init() throws Exception{
		////初始化系统
		Application application = new Application();
		application.init("WorldApplication.xml",application);
		GameLockManager.init(5 * 1000);
		//WorldServerCache.readCache();
	}
	private static final void start() throws Exception{
		init();
		RPCServerBootstrap.start(Application.RPC_PORT);
		HttpServerBootStrap.start(Integer.valueOf(Application.getPayPort()), new PayProcessHanlder());
	}
	public static void main(String[] args) throws Exception {
		final long start = System.currentTimeMillis();
		try {
			start();
		} catch (Exception e) {
			logger.error("", e);
			throw e;
		}		
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			@Override
			public void run() {
				logger.info("World Server shut down..");
				logger.info("start run time : " + new Timestamp(start));
				logger.info("end run time ->" + new Timestamp(System.currentTimeMillis()));
			}
		}));
	}
}