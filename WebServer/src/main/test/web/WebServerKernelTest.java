package web;


import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.webapp.WebAppClassLoader;
import org.eclipse.jetty.webapp.WebAppContext;

import com.ks.app.ServerEngine;
import com.ks.logger.LoggerFactory;
import com.living.web.jetty.WebServerKernel;

/**
 * 
 * @author living.li
 * @date  2014年4月17日
 */
public class WebServerKernelTest   {
	private static final Logger logger = LoggerFactory.get(WebServerKernelTest.class);
	
	private static final  Properties  serverInfoConfig=new Properties();	
	private static WebAppContext  webapp;
	
	public static String getConfig(String key) {
		return serverInfoConfig.getProperty(key);
	}
	public static String getConfig(String key,String defalut) {
		return serverInfoConfig.getProperty(key,defalut);
	}

	private static void initClassLoader() throws Exception{
		
		webapp=new WebAppContext("src/main/webapp/","/");
		WebAppClassLoader webClassLoader=new WebAppClassLoader(webapp);
		//webClassLoader.addClassPath("webClassLoader");
		webapp.setClassLoader(webClassLoader);
		ServerEngine.setClassLoader(webClassLoader);
	}
	private static void loadPropertys() throws Exception{
		logger.info("loading property config.................");
		serverInfoConfig.load(new FileInputStream(new File("conf"+File.separatorChar+"ServerInfo.properties")));		
		LoggerFactory.setFile(serverInfoConfig.getProperty("logger.file"));
		LoggerFactory.setLevel(serverInfoConfig.getProperty("logger.level")); 
		WebServerKernel.setServerInfoProperty(serverInfoConfig);		
	}
	public static void startJetty() throws Exception{
        String jettyPort = serverInfoConfig.getProperty("jetty.port","");
        System.out.println("jetty server start listening port: [" + jettyPort +"]");
        Server server = new Server();       
        Connector connector=new SelectChannelConnector();
        connector.setPort(Integer.valueOf(jettyPort));
        server.setConnectors(new Connector[]{connector});
       /* webapp.setExtractWAR(true);
        webapp.setCopyWebDir(true);
        webapp.setCopyWebInf(true);*/
        webapp.setDefaultsDescriptor("conf/webdefault.xml");
        webapp.setTempDirectory(new File("./target/"));
        server.setHandler(webapp);       
        server.start();
        server.join();
        
	}

	public static void main(String[] args) {		
		try {
			initClassLoader();
			loadPropertys();
			startJetty();
		} catch (Exception e) {
			logger.error("",e);
			System.exit(1);
		}		
	}
}


