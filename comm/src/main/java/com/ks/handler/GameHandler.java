package com.ks.handler;

import org.apache.log4j.Logger;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.jboss.netty.channel.WriteCompletionEvent;

import com.ks.game.model.Player;
import com.ks.logger.LoggerFactory;
import com.ks.protocol.Subpackage;
import com.ks.protocol.vo.Head;
/**
 * 
 * @author ks
 */
public class GameHandler extends SimpleChannelHandler {
	private static final Logger logger = LoggerFactory.get(GameHandler.class);
	protected Head head;
	protected Player player;
	protected Channel channel;
	protected Subpackage subpackage;
	protected int state=1;
	public final void setState(int state) {
		this.state = state;
	}
	public final Player getPlayer() {
		return player;
	}
	public final void setPlayer(Player player) {
		this.player = player;
		if(player!=null){
			player.setGameHandler(this);
		}
	}
	public Head getHead() {
		return head;
	}
	public void setHead(Head head) {
		this.head = head;
	}
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e)
			throws Exception {
		
		if(e.getCause() instanceof java.io.IOException){
			logger.warn("channel closed");
		}else{
			logger.error("", e.getCause());
		}
	}
	@Override
	public void writeComplete(ChannelHandlerContext ctx, WriteCompletionEvent e)
			throws Exception {
		if(state==2){
			if(e.getChannel().isConnected()){
				e.getChannel().close();
			}
		}
		super.writeComplete(ctx, e);
	}
	public final Channel getChannel() {
		return channel;
	}
	
}
