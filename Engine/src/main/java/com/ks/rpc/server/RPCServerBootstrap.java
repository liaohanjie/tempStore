package com.ks.rpc.server;

import java.net.InetSocketAddress;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.codec.serialization.ClassResolver;
import org.jboss.netty.handler.codec.serialization.ObjectDecoder;
import org.jboss.netty.handler.codec.serialization.ObjectEncoder;

import com.ks.app.ServerEngine;

/**
 * RPC服务器启动程序
 * @author ks
 */
public final class RPCServerBootstrap {
	/**
	 * 启动服务器
	 * @param port 服务器所监听的端口
	 */
	public static void start(int port){
		Executor bossExecutor = Executors.newCachedThreadPool();
		Executor workExecutor = Executors.newCachedThreadPool();
		ChannelFactory factory = new NioServerSocketChannelFactory(bossExecutor, workExecutor);
		ServerBootstrap bootstrap = new ServerBootstrap(factory);
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
				popeline.addLast("handler", new RPCServer());
				return popeline;
			}
		});
		bootstrap.setOption("child.tcpNoDelay" , true);
		bootstrap.setOption("child.keepAlive", true);
		bootstrap.bind(new InetSocketAddress(port));
	}
}
