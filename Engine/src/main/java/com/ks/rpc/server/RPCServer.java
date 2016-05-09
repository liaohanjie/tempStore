package com.ks.rpc.server;

import org.apache.log4j.Logger;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.jboss.netty.channel.WriteCompletionEvent;

import com.ks.app.GameWorkExecutor;
import com.ks.logger.LoggerFactory;
import com.ks.rpc.RPCComm;

/**
 * 远程调用服务端
 * @author ks
 *
 */
public class RPCServer extends SimpleChannelHandler {
	private static final Logger logger = LoggerFactory.get(RPCServer.class);
	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
			throws Exception {
		RPCComm comm = (RPCComm) e.getMessage();
		RPCServerCommand command = new RPCServerCommand(comm, e.getChannel());
		if(comm.isAsync()){
			command.run();
		}else{
			GameWorkExecutor.execute(command);
		}
		super.messageReceived(ctx, e);
	}

	@Override
	public void writeComplete(ChannelHandlerContext ctx, WriteCompletionEvent e)
			throws Exception {
		super.writeComplete(ctx, e);
	}

	@Override
	public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e)
			throws Exception {
		logger.info("client connected <<<<<<<<<<<<<<"+e.getChannel().getRemoteAddress());
		super.channelConnected(ctx, e);
	}
	
	@Override
	public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e)
			throws Exception {
		super.channelClosed(ctx, e);
	}
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e)
			throws Exception {
		if(!(e.getCause() instanceof java.io.IOException)){
			Exception ex = (Exception) e.getCause();
			while(ex.getCause()!=null){
				ex = (Exception) ex.getCause();
			}
			logger.error("",e.getCause());
		}else{
			logger.warn(e.getCause().getMessage());
		}
	}
}
