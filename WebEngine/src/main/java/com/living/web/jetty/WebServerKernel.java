package com.living.web.jetty;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Timestamp;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.handler.RequestLogHandler;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.webapp.WebAppClassLoader;
import org.eclipse.jetty.webapp.WebAppContext;

import com.ks.app.ServerEngine;
import com.ks.logger.LoggerFactory;
import com.living.web.core.WebRequestLog;

/**
 * 
 * @author living.li
 * @date 2014年4月17日
 */
public class WebServerKernel {
	private static final Logger logger = LoggerFactory.get(WebServerKernel.class);

	private static Properties serverInfoConfig = new Properties();
	private static WebAppContext webapp;

	public static String getConfig(String key) {
		return serverInfoConfig.getProperty(key);
	}

	public static String getConfig(String key, String defalut) {
		return serverInfoConfig.getProperty(key, defalut);
	}

	private static void initClassLoader() throws Exception {
		logger.info("init class loader ........");
		webapp = new WebAppContext();
		WebAppClassLoader loader = new WebAppClassLoader(webapp);
		loader.addClassPath("./lib");
		webapp.setClassLoader(loader);
		ServerEngine.setClassLoader(loader);
	}

	private static void loadPropertys() throws Exception {
		
		serverInfoConfig.load(new FileInputStream(new File("conf"+ File.separatorChar + "ServerInfo.properties")));
		LoggerFactory.setFile(serverInfoConfig.getProperty("logger.file"));
		LoggerFactory.setLevel(serverInfoConfig.getProperty("logger.level"));
		logger.info("loading property config end");
	}

	public static void setServerInfoProperty(Properties serverConfig) {
		serverInfoConfig = serverConfig;
	}
	public static void startJetty() throws Exception {
		String jettyHome = serverInfoConfig.getProperty("jetty.home", "");
		String jettyPort = serverInfoConfig.getProperty("jetty.port", "");
		String warName = serverInfoConfig.getProperty("jetty.war", "");
		
		//log
		WebRequestLog requestLog = new WebRequestLog();
		requestLog.setAppend(false);
		requestLog.setExtended(false);
		requestLog.setLogDispatch(false);
		requestLog.setLogCookies(false);
		requestLog.SetNot200Warn(true);
		
		//questHanlder
		RequestLogHandler requestLogHandler = new RequestLogHandler();
		requestLogHandler.setRequestLog(requestLog);
		
		//hanlder
		HandlerCollection handlers = new HandlerCollection();
		handlers.addHandler(webapp);
		handlers.addHandler(new DefaultHandler());
		handlers.addHandler(requestLogHandler);

		//webapp
		webapp.setContextPath("/");
		webapp.setWar(jettyHome + "/war/" + warName);
		webapp.setDefaultsDescriptor("conf/webdefault.xml");
		File f=new File("./work");
		if(f.exists()){
			f.delete();
		}else{
			f.mkdir();
		}
		// webapp.setParentLoaderPriority(true);
		// webapp.setConfigurationDiscovered(true);
		// webapp.setCopyWebDir(true);
		// webapp.setCopyWebInf(true);
		Server server = new Server();
		Connector connector = new SelectChannelConnector();
		connector.setPort(Integer.valueOf(jettyPort));
		server.setConnectors(new Connector[] { connector });
		server.setHandler(handlers);
		server.start();
		server.join();
	}

	public static void main(String[] args) throws Exception {
			final long start = System.currentTimeMillis();
			logger.info("start :"+start);
			Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
				@Override
				public void run() {
					logger.info("Web Server shut down..");
					logger.info("start run time : " + new Timestamp(start));
					logger.info("end run time ->"+ new Timestamp(System.currentTimeMillis()));
				}
			}));
			loadPropertys();
			initClassLoader();
			startJetty();
			
	}
	
	public static void localStart(){
		try{
			serverInfoConfig.load(new FileInputStream(new File("conf"+File.separatorChar+"ServerInfo.properties")));		
			LoggerFactory.setFile(serverInfoConfig.getProperty("logger.file"));
			LoggerFactory.setLevel(serverInfoConfig.getProperty("logger.level")); 
			webapp = new WebAppContext("src/main/webapp/","/");
			WebAppClassLoader webClassLoader = new WebAppClassLoader(webapp);
			webapp.setClassLoader(webClassLoader);
	        Server server = new Server();       
	        Connector connector=new SelectChannelConnector();
	        connector.setPort(Integer.valueOf(serverInfoConfig.getProperty("jetty.port","")));
	        server.setConnectors(new Connector[]{connector});
	        webapp.setDefaultsDescriptor("conf/webdefault.xml");
	        webapp.setTempDirectory(new File("./target/"));
	        server.setHandler(webapp);       
	        server.start();
	        server.join();
		}catch(Exception e){
			e.printStackTrace();
			System.exit(1);
		}
	}
}
