package com.ks.login.kernel;

import java.nio.ByteOrder;

import org.apache.log4j.Logger;
import org.jboss.netty.buffer.HeapChannelBufferFactory;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.MessageEvent;

import com.ks.app.GameWorkExecutor;
import com.ks.handler.GameHandler;
import com.ks.logger.LoggerFactory;
import com.ks.protocol.GameWorker;
import com.ks.protocol.Subpackage;
import com.ks.protocol.codec.message.obj.MessageObject;

public final class LoginServerHandler extends GameHandler {

	private String username;
	private static final Logger logger = LoggerFactory.get(LoginServerHandler.class);
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	@Override
	public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e)
			throws Exception {
		logger.info("Client disconnected login server");
		super.channelClosed(ctx, e);
	}
	@Override
	public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e)
			throws Exception {
		logger.info("Client connected login server");
		this.channel = e.getChannel();
		this.channel.getConfig().setBufferFactory(HeapChannelBufferFactory.getInstance(ByteOrder.LITTLE_ENDIAN));
		this.subpackage = new Subpackage(this);
		super.channelConnected(ctx, e);
	}

	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
			throws Exception {
		GameWorker worker = new GameWorker(subpackage, (MessageObject) e.getMessage());
		GameWorkExecutor.execute(worker);
		super.messageReceived(ctx, e);
	}
}