package com.ks.rpc.client;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.ks.app.ServerEngine;
import com.ks.exception.EngineException;
import com.ks.logger.LoggerFactory;
import com.ks.rpc.Async;
import com.ks.rpc.RPCComm;
import com.ks.rpc.RPCKernel;
import com.ks.rpc.RPCLock;
import com.ks.rpc.Timeout;
/**
 * 客户端调用handler
 * @author ks
 */
public class ClientRPCHandler implements InvocationHandler {
	private static final Logger logger = LoggerFactory.get(ClientRPCHandler.class);
	/**远程调用名称*/
	private String name;
	/**对象类型*/
	private int flag;
	/**服务器类型类型值*/
	private int type;
	/**服务器编号*/
	private String serverId;
	public ClientRPCHandler(String name,
			int flag,
			int type,
			String serverId) {
		this.name = name;
		this.flag = flag;
		this.type = type;
		this.serverId = serverId;
	}
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		switch (method.getName()) {
		case "toString":
			
			return serverId;
		case "hashCode":
			return serverId.hashCode();
		default:
			break;
		}
		
		if(method.isAnnotationPresent(Async.class)){
			return asyncMethod(proxy, method, args);
		}
		long timeOutTime = RPCComm.TIME_OUT_TIME;
		Timeout ann = method.getAnnotation(Timeout.class);
		if(ann!=null){
			timeOutTime = ann.time();
		}
		RPCComm comm = new RPCComm();
		comm.setArgs(args);
		comm.setClassName(name);
		comm.setMethodName(method.getName());
		comm.setStartTime(System.currentTimeMillis());
		comm.setId(RPCKernel.getRpcId());
		comm.setLock(new RPCLock());
		comm.setArgsClass(method.getParameterTypes());
		RPCClient client = null;
		switch (flag) {
		case RPCKernel.FLAG_TYPE:
			client = RPCKernel.getRPCClientByType(type);
			break;
		case RPCKernel.FLAG_ID:
			client = RPCKernel.getRPCClientById(serverId);
			break;
		default:
			break;
		}
		comm.getLock().lock();
		if(client==null){ 
			logger.error("client no found ");
			throw new EngineException(EngineException.CODE_NO_CLIENT
					,serverId+" client  no found request:"+name+"."+method.getName());
		}
		try{
			RPCKernel.setMapper(comm);
			client.sendMessage(comm);
			if(ServerEngine.SERVVER_STATUS){//发布状态监测超时时间
				boolean timeOut = comm.getLock().await(timeOutTime, TimeUnit.MILLISECONDS);
				if(!timeOut){
//					throw new RuntimeException(name+"."+method.getName()+" request time out."+(System.currentTimeMillis()-comm.getStartTime()));
					throw new RuntimeException("[" + method.getName() + "]服务器回发超时:"+(System.currentTimeMillis()-comm.getStartTime()) + "ms");
				}
			}else{//开发状态不检测超时时间
				comm.getLock().await();
			}
			long time = System.currentTimeMillis()-comm.getStartTime();
			logger.info("return method <<<<<<<<"+name+"."+method.getName()+"--->time:"+time);
			if(comm.getReturnValue() instanceof Throwable){
				throw (Throwable)comm.getReturnValue();
			}
		}finally{
			RPCKernel.removeComm(comm.getId());
			comm.getLock().unlock();
		}
		return comm.getReturnValue();
	}
	
	private Object asyncMethod(Object proxy, Method method, Object[] args)throws Throwable {
		if(!method.getGenericReturnType().toString().equals("void")){
			throw new RuntimeException("async mothod return type must be void..");
		}
		RPCComm comm = new RPCComm();
		comm.setArgs(args);
		comm.setClassName(name);
		comm.setMethodName(method.getName());
		comm.setStartTime(System.currentTimeMillis());
		comm.setId(RPCKernel.getRpcId());
		comm.setArgsClass(method.getParameterTypes());
		comm.setAsync(true);
		RPCClient client = null;
		switch (flag) {
		case RPCKernel.FLAG_TYPE:
			client = RPCKernel.getRPCClientByType(type);
			break;
		case RPCKernel.FLAG_ID:
			client = RPCKernel.getRPCClientById(serverId);
			break;
		default:
			break;
		}
		client.sendMessage(comm);
		return comm.getReturnValue();
	}
}
