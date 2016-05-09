package com.ks.game.kernel;

import java.nio.ByteOrder;

import org.jboss.netty.buffer.HeapChannelBufferFactory;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.MessageEvent;

import com.ks.app.GameWorkExecutor;
import com.ks.handler.GameHandler;
import com.ks.protocol.GameWorker;
import com.ks.protocol.Subpackage;
import com.ks.protocol.codec.message.obj.MessageObject;

public class GameServerHandler extends GameHandler {
	
	@Override
	public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e)
			throws Exception {
		super.channelClosed(ctx, e);
	}

	@Override
	public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e)
			throws Exception {
		this.channel = e.getChannel();
		this.channel.getConfig().setBufferFactory(HeapChannelBufferFactory.getInstance(ByteOrder.LITTLE_ENDIAN));
		subpackage = new Subpackage(this);
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
