package com.ks.login.kernel;

import java.net.InetSocketAddress;
import java.sql.Timestamp;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.timeout.IdleStateHandler;
import org.jboss.netty.util.HashedWheelTimer;
import org.jboss.netty.util.Timer;

import com.ks.app.Application;
import com.ks.handler.Heartbeat;
import com.ks.logger.LoggerFactory;
import com.ks.protocol.codec.message.MessageDecoder;
import com.ks.protocol.codec.message.SJMessageDecoder;
import com.ks.protocol.vo.Head;
import com.ks.rpc.RPCKernel;
import com.ks.rpc.client.RPCClient;

/**
 * 
 * @author ks
 *
 */
public final class LoginServerKernel {
	private static final Logger logger = LoggerFactory.get(LoginServerKernel.class);
	private static void init() throws Exception{
		Application application = new Application();
		application.init("LoginApplication.xml",application);
		logger.info("init application");
		RPCClient client = new RPCClient(new InetSocketAddress(Application.WORLD_SERVER_HOST,
				Application.WORLD_SERVER_PORT), "", Application.WORLD_SERVER);
		RPCKernel.addRPCClient(client);
	}
	private static void start() throws Exception{
		init();
		logger.info("start server");
		Executor bossExecutor = Executors.newCachedThreadPool();
		Executor workExecutor = Executors.newCachedThreadPool();
		ChannelFactory factory = new NioServerSocketChannelFactory(bossExecutor,workExecutor);
		ServerBootstrap bootstrap = new ServerBootstrap(factory);
		bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
			private Timer timer = new HashedWheelTimer(); 
			public ChannelPipeline getPipeline() {
				ChannelPipeline popeline = Channels.pipeline();
				popeline.addLast("decoder", new SJMessageDecoder(Head.class));
				popeline.addLast("timeout", new IdleStateHandler(timer, 10, 10, 0));
				popeline.addLast("hearbeat", new Heartbeat());
				popeline.addLast("handler", new LoginServerHandler());
				return popeline;
			}
		});
		bootstrap.setOption("child.tcpNoDelay", true);
		bootstrap.setOption("child.keepAlive", true);
		bootstrap.setOption("child.soLinger", 60);
		bootstrap.bind(new InetSocketAddress(Application.PORT));
		logger.info("server port:"+Application.PORT);
	}
	public static void main(String[] args) throws Exception {
		final long start = System.currentTimeMillis();
		start();
		
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			@Override
			public void run() {
				logger.info("Login Server shut down..");
				logger.info("start run time : " + new Timestamp(start));
				logger.info("end run time ->" + new Timestamp(System.currentTimeMillis()));
			}
		}));
	}
}
