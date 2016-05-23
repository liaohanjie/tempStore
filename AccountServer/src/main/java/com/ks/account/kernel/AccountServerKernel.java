package com.ks.account.kernel;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;

import com.ks.access.DataSourceUtils;
import com.ks.account.cache.AccountCache;
import com.ks.app.Application;
import com.ks.cache.JedisFactory;
import com.ks.logger.LoggerFactory;
import com.ks.model.account.ServerInfo;
import com.ks.rpc.RPCKernel;
import com.ks.rpc.client.ClientRPCHandler;
import com.ks.rpc.client.RPCClient;
import com.ks.rpc.server.RPCServerBootstrap;

/**
 * 
 * @author ks
 * 
 */
public final class AccountServerKernel {
	private static final Logger logger = LoggerFactory.get(AccountServerKernel.class);

	private AccountServerKernel() {
	}

	private static void initLog() throws Exception {
		Properties properties = new Properties();
		properties.load(new FileInputStream(new File("conf" + File.separatorChar + "ServerInfo.properties")));
		LoggerFactory.setFile(properties.getProperty("logger.file"));
		LoggerFactory.setLevel(properties.getProperty("logger.level"));
		logger.info("init log config end");
	}

	public static void initDataSource() throws Exception {
		logger.info("init data source ---------------------------------------");
		Properties properties = new Properties();
		properties.load(new FileInputStream(new File("conf" + File.separatorChar + "Datasource.properties")));

		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(properties.getProperty("driverClassName"));
		dataSource.setUrl(properties.getProperty("url"));
		dataSource.setUsername(properties.getProperty("username"));
		dataSource.setPassword(properties.getProperty("password"));

		dataSource.setInitialSize(Integer.parseInt(properties.getProperty("initialSize")));
		dataSource.setMaxIdle(Integer.parseInt(properties.getProperty("maxIdle")));
		dataSource.setMinIdle(Integer.parseInt(properties.getProperty("minIdle")));

		dataSource.setMaxActive(Integer.parseInt(properties.getProperty("maxActive")));
		dataSource.setMaxWait(Integer.parseInt(properties.getProperty("maxWait")));
		dataSource.setTestWhileIdle(Boolean.parseBoolean(properties.getProperty("testWhileIdle")));
		dataSource.setTestOnBorrow(Boolean.parseBoolean(properties.getProperty("testOnBorrow")));
		dataSource.setTestOnReturn(Boolean.parseBoolean(properties.getProperty("testOnReturn")));
		dataSource.setValidationQuery(properties.getProperty("validationQuery"));
		dataSource.setTimeBetweenEvictionRunsMillis(Long.parseLong(properties.getProperty("timeBetweenEvictionRunsMillis")));
		dataSource.setNumTestsPerEvictionRun(Integer.parseInt(properties.getProperty("numTestsPerEvictionRun")));
		dataSource.setDefaultAutoCommit(Boolean.parseBoolean(properties.getProperty("defaultAutoCommit")));
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

		logger.info("init data source--------------------------------------end");
		JedisFactory.init();
	}

	private static void initWorldRemote() throws Exception {
		List<ServerInfo> worldList = AccountCache.getServerList();
		for (ServerInfo info : worldList) {
			String serverId = info.getServerId().trim();
			final String ip = info.getWorldIp();
			final int port = info.getWorldPort();
			String remote = serverId + ":" + ip + ":" + port;
			logger.info("connect remote >>>>>>>>>>>>>>>>>" + remote);
			connectServer(serverId, ip, port, remote);
		}
	}

	public static void connectServer(String serverId, final String ip, final int port, String remote) {
		for (int i = 0; i < Runtime.getRuntime().availableProcessors(); i++) {
			RPCClient client = new RPCClient(new InetSocketAddress(ip, port), serverId, Application.WORLD_SERVER);
			RPCKernel.addRPCClient(client);
		}
		logger.info("init remote action:" + remote + " ---------------------------");
		Map<String, Class<?>> map = Application.RPC_CLIENT_MAPPER.get(Application.WORLD_SERVER);
		for (Map.Entry<String, Class<?>> e : map.entrySet()) {
			Object o = Proxy.newProxyInstance(e.getValue().getClassLoader(), new Class<?>[] { e.getValue() }, new ClientRPCHandler(e.getKey(), RPCKernel.FLAG_ID, Application.WORLD_SERVER, serverId));
			RPCKernel.addServerIDRemote(serverId, e.getValue(), o);
		}
	}

	public static void initApplication() throws Exception {
		Application application = new Application();
		application.init("AccountApplication.xml", application);
		RPCServerBootstrap.start(Application.RPC_PORT);
		logger.info("start  server on port:" + Application.RPC_PORT);

	}

	private static void initGameCache() {
		logger.info("init game cache...");
		AccountCache.init();
	}

	public static void main(String[] args) {
		final long start = System.currentTimeMillis();
		try {
			initLog();
			initDataSource();
			initApplication();
			initGameCache();
			initWorldRemote();
		} catch (Exception e) {
			logger.error("", e);
			System.exit(1);
		}

		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			@Override
			public void run() {
				logger.info("Game Server shut down..");
				logger.info("start run time : " + new Timestamp(start));
				logger.info("end run time ->" + new Timestamp(System.currentTimeMillis()));
			}
		}));
	}
}