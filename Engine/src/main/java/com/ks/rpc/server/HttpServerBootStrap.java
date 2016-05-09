/**
 * 
 */
package com.ks.rpc.server;

import java.net.InetSocketAddress;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.codec.http.HttpRequestDecoder;
import org.jboss.netty.handler.codec.http.HttpResponseEncoder;

/**
 * @author living.li
 * @date 2015年4月22日 下午1:49:18
 * 
 * 
 */
public class HttpServerBootStrap {
	/**
	 * 启动服务器
	 * 
	 * @param port
	 *            服务器所监听的端口
	 */
	public static void start(int port,final SimpleChannelHandler handler) {
		Executor bossExecutor = Executors.newCachedThreadPool();
		Executor workExecutor = Executors.newCachedThreadPool();
		ChannelFactory factory = new NioServerSocketChannelFactory(
				bossExecutor, workExecutor);
		ServerBootstrap bootstrap = new ServerBootstrap(factory);
		bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
			@Override
			public ChannelPipeline getPipeline() throws Exception {
				ChannelPipeline popeline = Channels.pipeline();
				popeline.addLast("decoder", new HttpRequestDecoder());
				popeline.addLast("encoder", new HttpResponseEncoder());
				popeline.addLast("handler", handler);
				return popeline;
			}
		});
		bootstrap.setOption("child.tcpNoDelay", true);
		bootstrap.setOption("child.keepAlive", true);
		bootstrap.bind(new InetSocketAddress(port));
	}
}
