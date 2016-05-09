package com.ks.app;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.ks.logger.LoggerFactory;
import com.ks.protocol.MessageProcess;
//import com.sun.tools.classfile.Opcode;
/**
 * 
 * @author ks
 *
 */
public final class Application extends ServerEngine{
	
	
	private static final Logger logger = LoggerFactory.get(Application.class);
	/**游戏服务器*/
	public static final int GAME_SERVER = 1;
	/**世界服务器*/
	public static final int WORLD_SERVER = 2;
	/**登录服务器*/
	public static final int LOGIN_SERVER = 3;
	/**逻辑服务器服务器*/
	public static final int LOGIC_SERVER = 4;
	/**Boss服务器*/
	public static final int BOSS_SERVER = 5;
	/**账号服务器*/
	public static final int ACCOUNT_SERVER = 6;
	
	/**服务器端口*/
	public static int PORT;
	/**远程调用端口*/
	public static int RPC_PORT;
	/**服务器主机名或IP*/
	public static String HOST;
	/**远程调用主机名或IP*/
	public static String RPC_HOST;
	/**世界服务器地址*/
	public static String WORLD_SERVER_HOST;
	/**世界服务器端口*/
	public static int WORLD_SERVER_PORT;
	
	/**最大工作线程数*/
	public static int MAXIMUM_POOL_SIZE;
	
	public static MessageProcess messageProcess;
	
	private String  dataSdkUrl;
	
	private String dataSdkServerId;

	/**服务器id(int，取dataSdkServerId最后的数字) */
	public static int DATA_SDKSERVERID_NO;
	/**最大在线人数*/
	public static int MAX_ONLINE_PLAYER;
	
	
	private static String payPort;
	/**数据统计开启*/
	private boolean dataOpen;
	
	/**不能注册*/
	public static boolean CANT_REGISTER;
	@Override
	public void addMessagePropcess(Object o) {
		messageProcess = (MessageProcess) o;
	}
	public void initApplication() throws Exception{
		if(!isInitVO()){
			return;
		}
		Properties properties = new Properties();
		properties.load(new FileInputStream(new File("conf"+File.separatorChar+"ServerInfo.properties")));
		LoggerFactory.setFile(properties.getProperty("logger.file"));
		LoggerFactory.setLevel(properties.getProperty("logger.level"));
		SERVVER_STATUS = Boolean.parseBoolean(properties.getProperty("status"));
		RPC_PORT = properties.getProperty("rpcPort")==null?0:Integer.parseInt(properties.getProperty("rpcPort"));
		PORT=properties.getProperty("port")==null?0:Integer.parseInt(properties.getProperty("port"));
		HOST=properties.getProperty("host");
		RPC_HOST = properties.getProperty("rpcHost");
		WORLD_SERVER_HOST = properties.getProperty("worldServerHost");
		WORLD_SERVER_PORT = properties.getProperty("worldServerPort")==null?0:Integer.parseInt(properties.getProperty("worldServerPort"));
		MAXIMUM_POOL_SIZE = properties.getProperty("maximumPoolSize")==null?Runtime.getRuntime().availableProcessors()*2:Integer.parseInt(properties.getProperty("maximumPoolSize"));
		CANT_REGISTER = properties.getProperty("cantRegister") == null ? false : Boolean.parseBoolean(properties.getProperty("cantRegister"));
		serverId = properties.getProperty("serverId");
		MAX_ONLINE_PLAYER = properties.getProperty("maxOnlinePlayer")==null?2000:Integer.parseInt(properties.getProperty("maxOnlinePlayer"));
		payPort=properties.getProperty("payPort");
		dataOpen=Boolean.valueOf(properties.getProperty("dataOpen"));
		this.dataSdkUrl=properties.getProperty("dataSdkUrl");
		this.dataSdkServerId=properties.getProperty("dataSdkServerId");
		
		//取 _s1后面的数字
		if(dataSdkServerId != null){
			int lastIndexOf = dataSdkServerId.lastIndexOf("_");
			DATA_SDKSERVERID_NO = Integer.parseInt(dataSdkServerId.substring(lastIndexOf + 2));
		}
		
		logger.info("dataOpen:"+dataOpen+" server_id:"+dataSdkServerId);
		GameWorkExecutor.initGameWorkExecutor(MAXIMUM_POOL_SIZE);
		logger.info("init server info");
		logger.info("Server Status : "+(SERVVER_STATUS?"Release":"Develop"));
	}
	@Override
	public boolean isInitVO() {
		return SERVER_TYPE != BOSS_SERVER;
	}
	public String getDataSdkUrl() {
		return dataSdkUrl;
	}
	public String getDataSdkServerId() {
		return dataSdkServerId;
	}
	public static String getPayPort(){
		return payPort;
	}
	public  boolean isDataOpen() {
		return dataOpen;
	}
}