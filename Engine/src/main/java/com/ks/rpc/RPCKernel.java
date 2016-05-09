package com.ks.rpc;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.log4j.Logger;

import com.ks.logger.LoggerFactory;
import com.ks.rpc.client.RPCClient;

/**
 * 远程调用核心
 * 
 * @author ks
 */
public final class RPCKernel {
	private static final Logger logger = LoggerFactory.get(RPCKernel.class);
	/**服务器类型*/
	public static final int FLAG_TYPE = 1;
	/**服务器ID*/
	public static final int FLAG_ID = 2;
	/** 远程调用id生产器 */
	private static final AtomicLong RPC_ID = new AtomicLong();
	/** 映射表 */
	private static final Map<Long, RPCComm> MAPPER = new ConcurrentHashMap<Long, RPCComm>(Runtime.getRuntime().availableProcessors()*8);
	/** 客户端映射表 key=serverId,value=客户端连接 */
	private static final Map<String, RPCClient> CLIENT_ID_MAP = new ConcurrentHashMap<String, RPCClient>();
	/** 客户端映射表 key=serverType,value=客户端连接 */
	private static final Map<Integer, BlockingQueue<RPCClient>> CLIENT_TYPE_MAP = new ConcurrentHashMap<Integer, BlockingQueue<RPCClient>>();
	/**接口映射*/
	private static final Map<String,Object> INTERFACE_MAP = new ConcurrentHashMap<String, Object>();
	/**服务器类型映射key=服务器类型 value=[key=类的class value=实现类实例]*/
	private static final Map<Integer,Map<Class<?>,Object>> SERVER_TYPE_REMOTE_MAP = new ConcurrentHashMap<Integer, Map<Class<?>,Object>>();
	/**服务器类型映射key=服务器ID value=[key=类的class value=实现类实例]*/
	private static final Map<String,Map<Class<?>,Object>> SERVER_ID_REMOTE_MAP = new ConcurrentHashMap<String, Map<Class<?>,Object>>();
	/**
	 * 获取远程调用编号
	 * 
	 * @return 远程调用编号
	 */
	public static long getRpcId() {
		return RPC_ID.incrementAndGet();
	}

	/**
	 * 设置命令
	 * 
	 * @param comm
	 *            命令
	 */
	public static void setMapper(RPCComm comm) {
		MAPPER.put(comm.getId(), comm);
	}

	/**
	 * 获取命令
	 * 
	 * @param id
	 *            编号
	 * @return 远程调用命令
	 */
	public static RPCComm getMapper(long id) {
		return MAPPER.get(id);
	}

	public static void removeComm(long id) {
		MAPPER.remove(id);
	}
	/**
	 * 根据服务器类型获取远程调用客户端
	 * @param serverType 服务器类型
	 * @return 客户端
	 * @throws Exception 
	 */
	public static RPCClient getRPCClientByType(int serverType) throws Exception{
		BlockingQueue<RPCClient> queue = CLIENT_TYPE_MAP.get(serverType);
		if(queue == null){
			return null;
		}
		return getRPCClientByType(queue, 0,serverType);
	}
	
	private static RPCClient getRPCClientByType(BlockingQueue<RPCClient> queue,int count, int serverType) throws Exception{
		RPCClient client = queue.poll(100,TimeUnit.MILLISECONDS);
		if(client!=null){
			if(!client.canUse()){
				queue.offer(client);
				client.reconnectAll();
				count += 1;
				if(count>=queue.size()){
					long start = System.currentTimeMillis();
					while(!client.canUse()){
						if(System.currentTimeMillis()-start>50)
							break;
					}
					if(client.canUse()){
						return client;
					}
					throw new Exception("there is no can use clent servertype."+serverType);
				}
				return getRPCClientByType(queue, count,serverType);
			}
			queue.offer(client);
		}
		return client;
	}
	
	/**
	 * 根据服务器编号获取远程调用客户端
	 * @param serverId 服务器编号
	 * @return 客户端
	 * @throws Exception 
	 */
	public static RPCClient getRPCClientById(String serverId) throws Exception{
		RPCClient client =  CLIENT_ID_MAP.get(serverId);
		if(client == null){
			throw new Exception("no node server ID : "+serverId);
		}
		return client;
	}
	/**
	 * 增加远程调用客户端
	 * @param client 远程调用客户端
	 */
	public synchronized static void addRPCClient(RPCClient client){
		if(CLIENT_ID_MAP.get(client.getServerId()) == null){
			CLIENT_ID_MAP.put(client.getServerId(), client);
			BlockingQueue<RPCClient> queue = CLIENT_TYPE_MAP.get(client.getServerType());
			if(queue == null){
				queue = new LinkedBlockingQueue<>();
				CLIENT_TYPE_MAP.put(client.getServerType(), queue);
			}
			queue.add(client);
		}else{
			RPCClient c = CLIENT_ID_MAP.get(client.getServerId());
			try {
				if(!c.canUse()){
					c.reconnectAll();
					Thread.sleep(50);
				}
			} catch (Exception e) {
				logger.error("",e);
			}
		}
	}
	/**
	 * 增加远程调用接口
	 * @param name 接口名
	 * @param o 接口实现类
	 */
	public static void addInterface(String name,Object o){
		INTERFACE_MAP.put(name, o);
	}
	/**
	 * 获取远程调用接口
	 * @param name 接口名
	 * @return 远程调用接口
	 */
	public static Object getInterface(String name){
		return INTERFACE_MAP.get(name);
	}
	/**
	 * 增加服务器类型远程调用类
	 * @param serverType 服务器类型
	 * @param clazz 类的class
	 * @param o 类的实现
	 */
	public static void addServerTypeRemote(int serverType,Class<?> clazz,Object o){
		Map<Class<?>,Object> om = SERVER_TYPE_REMOTE_MAP.get(serverType);
		if(om==null){
			om = new ConcurrentHashMap<Class<?>, Object>();
			SERVER_TYPE_REMOTE_MAP.put(serverType, om);
		}
		logger.info("add type remote : " + clazz);
		om.put(clazz, o);
	}
	/**
	 * 增加服务器ID远程调用类
	 * @param serverId 服务器ID
	 * @param clazz 类的class
	 * @param o 类的实现
	 */
	public static void addServerIDRemote(String serverId,Class<?> clazz,Object o){
		Map<Class<?>,Object> om = SERVER_ID_REMOTE_MAP.get(serverId);
		if(om==null){
			om = new ConcurrentHashMap<Class<?>, Object>();
			SERVER_ID_REMOTE_MAP.put(serverId, om);
		}
		om.put(clazz, o);
		logger.info("add id remote : "+serverId+":"+ clazz );
	}
	/**
	 * 根据服务器编号查找远程调用对象
	 * @param <T> 远程调用对象
	 * @param serverId 服务器编号
	 * @param clazz 远程调用对象class
	 * @return 远程调用对象
	 */
	@SuppressWarnings("unchecked")
	public static <T>T getRemoteByServerId(String serverId,Class<T> clazz){
		Map<Class<?>,Object> map = SERVER_ID_REMOTE_MAP.get(serverId);
		if(map != null){
			return (T)map.get(clazz);
		}
		return null;
	}
	/**
	 * 根据服务器类型查找远程调用对象
	 * @param <T> 远程调用对象
	 * @param serverType 服务器类型
	 * @param clazz 远程调用对象class
	 * @return 远程调用对象
	 */
	@SuppressWarnings("unchecked")
	public static <T>T getRemoteByServerType(int serverType,Class<T> clazz){
		Map<Class<?>,Object> map = SERVER_TYPE_REMOTE_MAP.get(serverType);
		if(map != null){
			return (T)map.get(clazz);
		}
		return null;
	}
}