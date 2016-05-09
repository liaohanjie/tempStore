package com.ks.rpc.server;

import java.lang.reflect.Method;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.jboss.netty.channel.Channel;

import com.ks.exception.EngineException;
import com.ks.logger.LoggerFactory;
import com.ks.rpc.Async;
import com.ks.rpc.RPCComm;
import com.ks.rpc.RPCKernel;
import com.ks.rpc.RPCResult;

/**
 * rpc服务器命令
 * @author ks
 */
public class RPCServerCommand implements Runnable {
	private static final Logger logger = LoggerFactory.get(RPCServer.class);
	private RPCComm comm;
	private Channel channel;
	
	public RPCServerCommand(RPCComm comm, Channel channel) {
		super();
		this.comm = comm;
		this.channel = channel;
	}

	@Override
	public void run() {
		long start = System.currentTimeMillis();
		Object result = null;
		if(LoggerFactory.getLevel()==Level.DEBUG){
			StringBuilder sb = new StringBuilder();
			sb.append("invoke method >>>>>>>> "+comm.getClassName()+"."+comm.getMethodName()+"\n");
			if(comm.getArgs()!=null){
				for(int i=0;i<comm.getArgs().length;i++){
					sb.append("\t args["+i+"] : "+comm.getArgs()[i]+"\n");
				}
			}
			sb.append("------------------------------------------------------\n");
			logger.debug(sb.toString());
		}
		
		try{
			Object o = RPCKernel.getInterface(comm.getClassName());
			Method m = o.getClass().getMethod(comm.getMethodName(), comm.getArgsClass());
			result = m.invoke(o, comm.getArgs());
			if(m.isAnnotationPresent(Async.class)){
				return;
			}
		}catch (Exception e1) {
			Throwable ex = e1;
			while(ex.getCause()!=null){
				ex=ex.getCause();
			}
			if(ex instanceof EngineException){
				logger.warn("", ex);				
			}else{
				logger.error("", ex);
			}
			result=ex;
		}
//		if(LoggerFactory.getLevel()==Level.DEBUG){
//			StringBuilder sb = new StringBuilder();
//			sb.append("return method <<<<<<<< "+comm.getClassName()+"."+comm.getMethodName()+" times : "+(System.currentTimeMillis()-start)+"\n");
//				sb.append('\t').append(result).append('\n');
//			sb.append("------------------------------------------------------\n");
//			logger.debug(sb);
//		}
		
		RPCResult res = new RPCResult();
		res.setId(comm.getId());
		res.setResult(result);
		channel.write(res);
	}

}
