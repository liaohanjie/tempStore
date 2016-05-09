package com.ks.app;

import org.jboss.netty.channel.Channel;

import com.ks.protocol.AbstractHead;

public interface BeforeSendMessage {
	/**
	 * 发送消息之前
	 * @param channel 通道
	 * @param head 消息头
	 * @param args 消息体
	 */
	void beforeSendMessage(Channel channel, AbstractHead head,Object...args);
}
