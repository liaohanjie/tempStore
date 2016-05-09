package com.ks.protocol.codec.message.obj;

import org.jboss.netty.buffer.ChannelBuffer;

import com.ks.protocol.AbstractHead;

public class MessageObject {
	/**包头*/
	private AbstractHead head;
	/**数据区*/
	private ChannelBuffer buffers;
	public AbstractHead getHead() {
		return head;
	}
	public void setHead(AbstractHead head) {
		this.head = head;
	}
	public ChannelBuffer getBuffers() {
		return buffers;
	}
	public void setBuffers(ChannelBuffer buffers) {
		this.buffers = buffers;
	}
}
