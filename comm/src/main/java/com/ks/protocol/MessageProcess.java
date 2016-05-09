package com.ks.protocol;

import org.jboss.netty.buffer.ChannelBuffer;

import com.ks.handler.GameHandler;

/**
 * 
 * @author ks
 *
 */
public abstract class MessageProcess {
	/**
	 * 消息处理
	 * @param gameHandler 要处理的玩家
	 * @param head 消息头
	 * @param channelBuffer 数据区
	 */
	public abstract void process(GameHandler gameHandler,AbstractHead head,ChannelBuffer channelBuffer) throws Exception;
}
