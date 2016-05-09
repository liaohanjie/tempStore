package com.ks.handler;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.timeout.IdleState;
import org.jboss.netty.handler.timeout.IdleStateAwareChannelHandler;
import org.jboss.netty.handler.timeout.IdleStateEvent;
/**
 * 心跳控制
 * @author ks
 */
public class Heartbeat extends IdleStateAwareChannelHandler {
	int i = 0;
	@Override
	public void channelIdle(ChannelHandlerContext ctx, IdleStateEvent e)
			throws Exception {
		super.channelIdle(ctx, e);

		if (e.getState() == IdleState.WRITER_IDLE){
			i++;
		}
		if (i == 2) {
			e.getChannel().close();
		}
	}
}