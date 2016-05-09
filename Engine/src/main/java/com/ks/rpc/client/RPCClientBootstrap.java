package com.ks.rpc.client;

import java.net.SocketAddress;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.handler.codec.serialization.ClassResolver;
import org.jboss.netty.handler.codec.serialization.ObjectDecoder;
import org.jboss.netty.handler.codec.serialization.ObjectEncoder;

import com.ks.app.ServerEngine;

/**
 * RPC客户端连接
 * @author ks
 */
public final class RPCClientBootstrap {
	private static ClientBootstrap bootstrap;
	static{
		init();
	}
	private static void init(){
		Executor bossExecutor = Executors.newCachedThreadPool();
		Executor workExecutor = Executors.newCachedThreadPool();
		ChannelFactory factory = new NioClientSocketChannelFactory(bossExecutor, workExecutor);
		ClientBootstrap bootstrap = new ClientBootstrap(factory);
		bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
			@Override
			public ChannelPipeline getPipeline() throws Exception {
				ChannelPipeline popeline = Channels.pipeline();
				popeline.addLast("decoder", new ObjectDecoder(new ClassResolver() {
					
					@Override
					public Class<?> resolve(String className) throws ClassNotFoundException {
						return ServerEngine.getClassLoader().loadClass(className);
					}
				}));
				popeline.addLast("encoder", new ObjectEncoder());
				popeline.addLast("handler", new RPCClientChannelHandler());
				return popeline;
			}
		});
		bootstrap.setOption("tcpNoDelay" , true);
		bootstrap.setOption("keepAlive", true);
		RPCClientBootstrap.bootstrap=bootstrap;
	}
	
	protected static RPCClientChannelHandler connect(
			SocketAddress address,
			String serverId,
			int serverType) throws Exception{
		ChannelFuture future = bootstrap.connect(address);
		RPCClientChannelHandler handler = (RPCClientChannelHandler) future.getChannel().getPipeline().get("handler");
		synchronized (handler) {
			String sd=serverId;
			handler.setServerId(sd);
			handler.setServerType(serverType);
			if(!handler.isState()){
				handler.wait(1000);
			}
			return handler;
		}
	}
}
