package com.ks.logic.kernel;

import java.io.File;
import java.io.FileInputStream;
import java.net.InetSocketAddress;
import java.sql.Timestamp;
import java.util.Properties;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;

import com.ks.access.DataSourceUtils;
import com.ks.action.world.WorldServerAction;
import com.ks.app.Application;
import com.ks.cache.JedisFactory;
import com.ks.logger.LoggerFactory;
import com.ks.logic.cache.GameCache;
import com.ks.rpc.RPCKernel;
import com.ks.rpc.client.RPCClient;
import com.ks.rpc.server.RPCServerBootstrap;
import com.sf.data.DataSDK;

/**
 * 
 * @author ks
 *
 */
public final class LogicServerKernel {
	private static final Logger logger = LoggerFactory.get(LogicServerKernel.class);
	private LogicServerKernel(){}
	public static void initDataSource() throws Exception{
		logger.info("init data source...");
		Properties properties = new Properties();
		properties.load(new FileInputStream(new File("conf"+File.separatorChar+"Datasource.properties")));
		
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setUrl(properties.getProperty("url"));
		dataSource.setUsername(properties.getProperty("username"));
		dataSource.setPassword(properties.getProperty("password"));
		dataSource.setInitialSize(Integer.parseInt(properties.getProperty("initialSize")));
		
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setMaxIdle(Runtime.getRuntime().availableProcessors()*2);
		dataSource.setMinIdle(Runtime.getRuntime().availableProcessors()*2);
		dataSource.setMaxActive(Runtime.getRuntime().availableProcessors()*2);
		
		dataSource.setMaxWait(60000);
		dataSource.setTestWhileIdle(true);
		dataSource.setTestOnBorrow(true);
		dataSource.setTestOnReturn(true);
		dataSource.setValidationQuery("select 1");
		dataSource.setTimeBetweenEvictionRunsMillis(3600000);
		dataSource.setNumTestsPerEvictionRun(Runtime.getRuntime().availableProcessors());
		dataSource.setDefaultAutoCommit(true);
		
		DataSourceUtils.setDataSource(dataSource);
		
		/**初始化配置数据源*/
		Properties cfgproperties = new Properties();
		cfgproperties.load(new FileInputStream(new File("conf"+File.separatorChar+"CfgDatasource.properties")));
		BasicDataSource cfgDataSource = new BasicDataSource();
		cfgDataSource.setUrl(cfgproperties.getProperty("url"));
		cfgDataSource.setUsername(cfgproperties.getProperty("username"));
		cfgDataSource.setPassword(cfgproperties.getProperty("password"));
		cfgDataSource.setInitialSize(Integer.parseInt(cfgproperties.getProperty("initialSize")));
		
		cfgDataSource.setDriverClassName("com.mysql.jdbc.Driver");
		cfgDataSource.setMaxIdle(Runtime.getRuntime().availableProcessors()*2);
		cfgDataSource.setMinIdle(Runtime.getRuntime().availableProcessors()*2);
		cfgDataSource.setMaxActive(Runtime.getRuntime().availableProcessors()*2);
		
		cfgDataSource.setMaxWait(60000);
		cfgDataSource.setTestWhileIdle(true);
		cfgDataSource.setTestOnBorrow(true);
		cfgDataSource.setTestOnReturn(true);
		cfgDataSource.setValidationQuery("select 1");
		cfgDataSource.setTimeBetweenEvictionRunsMillis(3600000);
		cfgDataSource.setNumTestsPerEvictionRun(Runtime.getRuntime().availableProcessors());
		cfgDataSource.setDefaultAutoCommit(true);
		
		DataSourceUtils.setCfgDataSource(cfgDataSource);
		
		/**初始化动态配置数据源*/
		Properties dcfgproperties = new Properties();
		dcfgproperties.load(new FileInputStream(new File("conf"+File.separatorChar+"DynamicCfgDatasource.properties")));
		BasicDataSource dcfgDataSource = new BasicDataSource();
		dcfgDataSource.setUrl(dcfgproperties.getProperty("url"));
		dcfgDataSource.setUsername(dcfgproperties.getProperty("username"));
		dcfgDataSource.setPassword(dcfgproperties.getProperty("password"));
		dcfgDataSource.setInitialSize(Integer.parseInt(dcfgproperties.getProperty("initialSize")));
		
		dcfgDataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dcfgDataSource.setMaxIdle(Runtime.getRuntime().availableProcessors()*2);
		dcfgDataSource.setMinIdle(Runtime.getRuntime().availableProcessors()*2);
		dcfgDataSource.setMaxActive(Runtime.getRuntime().availableProcessors()*2);
		
		dcfgDataSource.setMaxWait(60000);
		dcfgDataSource.setTestWhileIdle(true);
		dcfgDataSource.setTestOnBorrow(true);
		dcfgDataSource.setTestOnReturn(true);
		dcfgDataSource.setValidationQuery("select 1");
		dcfgDataSource.setTimeBetweenEvictionRunsMillis(3600000);
		dcfgDataSource.setNumTestsPerEvictionRun(Runtime.getRuntime().availableProcessors());
		dcfgDataSource.setDefaultAutoCommit(true);
		
		DataSourceUtils.setDynamicDataSource(dcfgDataSource);
		
		JedisFactory.init();
	}
	private static void init() throws Exception{
		Application application = new Application();
		application.init("DatabaseApplication.xml",application);
		initDataSource();
		initGameCache();
		RPCClient client = new RPCClient(new InetSocketAddress(Application.WORLD_SERVER_HOST,
				Application.WORLD_SERVER_PORT), "", Application.WORLD_SERVER);
		RPCKernel.addRPCClient(client);
		
		RPCServerBootstrap.start(Application.RPC_PORT);
		RPCKernel.getRemoteByServerType(Application.WORLD_SERVER, WorldServerAction.class).
		logicServerConndeted(Application.RPC_HOST,Application.RPC_PORT,Application.serverId);
		DataSDK.setConfig(application.getDataSdkUrl(), application.getDataSdkServerId(),100,application.isDataOpen());
		
		logger.info("dataOpen:"+application.isDataOpen());
	}
	private static void startServer() throws Exception{
		init();
	}
	public static void initGameCache(){
		logger.info("init game cache...");
		GameCache.init();
	}
	public static void main(String[] args) throws Exception {
		final long start = System.currentTimeMillis();
		startServer();
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			@Override
			public void run() {
				logger.info("Logic Server shut down..");
				logger.info("start run time : " + new Timestamp(start));
				logger.info("end run time ->" + new Timestamp(System.currentTimeMillis()));
			}
		}));
	}
}