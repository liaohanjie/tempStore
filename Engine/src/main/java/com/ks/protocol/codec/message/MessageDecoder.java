package com.ks.protocol.codec.message;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;

import com.ks.app.ServerEngine;
import com.ks.exception.EngineException;
import com.ks.protocol.AbstractHead;
import com.ks.protocol.MessageFactory;
import com.ks.protocol.codec.message.obj.MessageObject;
/**
 * 消息解码
 * @author ks
 */
public class MessageDecoder extends FrameDecoder {
	public final static int DEFAULT_LEN = 1024 * 2;
//	public static int DEFAULT_LEN = 1;
	public final static int MAX_LEN = Integer.MAX_VALUE;
	private Class<? extends AbstractHead> headClass;
	private int maxLen = DEFAULT_LEN;
	
	public MessageDecoder(Class<? extends AbstractHead> headClass) {
		super();
		this.headClass = headClass;
	}
	
	public MessageDecoder(Class<? extends AbstractHead> headClass, int maxLen) {
		super();
		this.headClass = headClass;
		this.maxLen = maxLen;
	}

	@Override
	protected Object decode(ChannelHandlerContext ctx, Channel channel,
			ChannelBuffer buffer) throws Exception {
		if(buffer.readableBytes() >= ServerEngine.HEAD_LENGTH){
			buffer.markReaderIndex();
			AbstractHead head = MessageFactory.getMessage(headClass);
			head.decode(buffer);
			if(head.getLength() > maxLen){
				throw new EngineException(EngineException.CODE_SYSTEM_EXCETION, "消息长度溢出!");
			}
			if(head.getLength()>buffer.readableBytes()){
				buffer.resetReaderIndex();
				return null;
			}
			ChannelBuffer frame = buffer.readBytes(head.getLength());
			
			MessageObject obj = new MessageObject();
			obj.setHead(head);
			obj.setBuffers(frame);
			return obj;
		}
		return null;
	}

}
