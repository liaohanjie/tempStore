package com.ks.rpc.client;

import org.apache.log4j.Logger;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.jboss.netty.channel.WriteCompletionEvent;

import com.ks.logger.LoggerFactory;
import com.ks.rpc.RPCComm;
import com.ks.rpc.RPCKernel;
import com.ks.rpc.RPCResult;
/**
 * 远程调用客户端channel handler
 * @author ks
 */
public final class RPCClientChannelHandler extends SimpleChannelHandler {
	
	private static final Logger logger = LoggerFactory.get(RPCClientChannelHandler.class);
	
	/**通讯通道*/
	private Channel channel;
	/**服务器ID*/
	private String serverId;
	/**服务器类型*/
	private int serverType;
	/**状态*/
	private boolean state=false;
	public void setServerId(String serverId) {
		this.serverId = serverId;
	}
	public void setServerType(int serverType) {
		this.serverType = serverType;
	}
	public String getServerId() {
		return serverId;
	}
	public int getServerType() {
		return serverType;
	}
	public boolean isState() {
		return state;
	}
	public Channel getChannel() {
		return channel;
	}
	/**
	 * 反回调用远程服务器结果
	 */
	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
			throws Exception {
		RPCResult comm = (RPCResult) e.getMessage();
		RPCComm currComm = RPCKernel.getMapper(comm.getId());
		if(currComm!=null){
			currComm.setReturnValue(comm.getResult());
			currComm.getLock().lock();
			try{
				currComm.getLock().signal();
			}finally{
				currComm.getLock().unlock();
			}
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
		this.channel = e.getChannel();
		logger.info("connect "+serverId + " success.");
		synchronized (this) {
			state=true;
			this.notifyAll();
		}
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
		if(e.getCause() instanceof java.net.ConnectException){
			logger.error("client error."+serverId,e.getCause());
		}else{
			logger.error("",e.getCause());
		}
	}
	/**
	 * 是否可用
	 * @return true 可用
	 */
	public boolean canUse(){
		return (channel!=null&&channel.isConnected());
	}
	
	public void sendMessage(RPCComm comm) throws Exception{
		channel.write(comm);
	}
}