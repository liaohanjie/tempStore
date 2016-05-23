package com.living.web.servlet;

import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;

import com.ks.logger.LoggerFactory;
import com.living.web.WebServer;
import com.living.web.remote.RemoteManager;

/**
 * 
 * @author living.li
 * @date 2014-3-3
 */
public class StartUpServlet extends HttpServlet {
	private Logger logger = LoggerFactory.get(StartUpServlet.class);
	private static final long serialVersionUID = 1L;

	@Override
	public void init() throws ServletException {
		super.init();
		try {
			WebServer.get().startUp();
			
			logger.info("int system start -------------------------");	
			String path = getInitParameter("WebAppConfig");
			InputStream in = getServletContext().getResourceAsStream(path);
			RemoteManager.initAccount(in);
			logger.info("int system end-----------------------------");
		} catch (Throwable e) {
			Throwable ex = e;
			while(ex.getCause()!=null){
				ex=ex.getCause();
			}
			throw new ServletException("init WebServer error:",ex);
		}
		new Thread(new StartUpRunnable()).start();
	}
	class StartUpRunnable implements  Runnable {
		public void run() {
			try {
				RemoteManager.initWorld();
			} catch (Exception e) {
				logger.error("",e);
			}
		};		
	}

}
