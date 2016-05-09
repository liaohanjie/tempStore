package com.ks.game.kernel;

import java.net.InetSocketAddress;
import java.sql.Timestamp;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.timeout.IdleStateHandler;
import org.jboss.netty.util.HashedWheelTimer;
import org.jboss.netty.util.Timer;

import com.ks.action.world.WorldServerAction;
import com.ks.app.Application;
import com.ks.app.BeforeSendMessage;
import com.ks.game.handler.ActionAdapter;
import com.ks.game.model.Player;
import com.ks.handler.Heartbeat;
import com.ks.logger.LoggerFactory;
import com.ks.manager.ClientLockManager;
import com.ks.protocol.AbstractHead;
import com.ks.protocol.codec.message.MessageDecoder;
import com.ks.protocol.codec.message.SJMessageDecoder;
import com.ks.protocol.vo.Head;
import com.ks.rpc.RPCKernel;
import com.ks.rpc.client.RPCClient;
import com.ks.rpc.server.RPCServerBootstrap;

public final class GameServerKernel {
	private static final Logger logger =LoggerFactory.get(GameServerKernel.class);
	
	private GameServerKernel() {
		
	}
	
	private static void init() throws Exception {
		Application application = new Application();
		application.init("GameApplication.xml",application);
		Application.setBeforeSendMessage(new BeforeSendMessage() {
			
			@Override
			public void beforeSendMessage(Channel channel, AbstractHead head,
					Object... args) {
				if(channel.getAttachment()!= null){
					Player player = (Player) channel.getAttachment();
					player.setLastSendHead(head);
					player.setLastSendBody(args);
				}
			}
		});
		logger.info("init application");
		logger.info("application init finish....");
		logger.info("connect world server....");
		
		RPCClient client = new RPCClient(new InetSocketAddress(Application.WORLD_SERVER_HOST, Application.WORLD_SERVER_PORT), "", Application.WORLD_SERVER);
		RPCKernel.addRPCClient(client);
		
		// 开启游戏服务端口服务
		logger.info("start rpc server——>"+Application.RPC_PORT);
		RPCServerBootstrap.start(Application.RPC_PORT);
		logger.info("start rpc server finish ...");
		
		ClientLockManager.init(ActionAdapter.lockAction());
		
		// 向 WorldServer 注册游戏服信息
		WorldServerAction action = RPCKernel.getRemoteByServerType(Application.WORLD_SERVER, WorldServerAction.class);
		action.gameServerConnected(Application.HOST, Application.PORT, Application.RPC_HOST, Application.RPC_PORT,Application.serverId);
	}
	
	private static void startServer() throws Exception {
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
				popeline.addLast("timeout", new IdleStateHandler(timer, 320, 320, 0));
				popeline.addLast("hearbeat", new Heartbeat());
				popeline.addLast("handler", new GameServerHandler());
				return popeline;
			}
		});
		bootstrap.setOption("child.tcpNoDelay", true);
		bootstrap.setOption("child.keepAlive", true);
		bootstrap.bind(new InetSocketAddress(Application.PORT));
		logger.info("start server finish....");
		logger.info("server port："+Application.PORT);
		logger.info("RPC address："+Application.RPC_HOST);
		logger.info("RPC port："+Application.RPC_PORT);
	}

	public static void main(String[] args) throws Exception {
		final long start = System.currentTimeMillis();
		try {
			startServer();
		} catch (Exception e) {
			logger.error("",e);
			System.exit(1);
		}
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			@Override
			public void run() {
				logger.info("Game Server shut down..");
				logger.info("start run time : " + new Timestamp(start));
				logger.info("end run time ->" + new Timestamp(System.currentTimeMillis()));
			}
		}));
	}

}