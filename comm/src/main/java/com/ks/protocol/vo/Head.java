package com.ks.protocol.vo;

import java.io.UnsupportedEncodingException;

import org.jboss.netty.buffer.ChannelBuffer;

import com.ks.protocol.AbstractHead;

/**
 * 包头
 * @author ks
 */
public class Head extends AbstractHead {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**数据区长度*/
	private short length;
	/**主命令*/
	private short mainCmd;
	/**子命令*/
	private short subCmd;
	/**session编号*/
	private long sessionId;
	/**效验字段*/
	private short checkVal;
	/**通知*/
	private int nofiti;
	/**是否被压缩*/
	private boolean compressed;
	/**序列号*/
	private long order;
	/**序列码*/
	private long serialization1;
	/**序列码*/
	private long serialization2;

	
	public void init(short mainCmd,short subCmd){
		this.mainCmd=mainCmd;
		this.subCmd = subCmd;
		super.setMainCmd(mainCmd);
		super.setSubCmd(subCmd);
		checkVal = 0;
//		sessionId = 0;
	}
	
	public long getOrder() {
		return order;
	}
	public void setOrder(long order) {
		this.order = order;
	}
	
	public long getSessionId() {
		return sessionId;
	}
	public int getNofiti() {
		return nofiti;
	}
	public void setNofiti(int nofiti) {
		this.nofiti = nofiti;
	}
	public void setSessionId(long sessionId) {
		this.sessionId = sessionId;
	}
	public short getCheckVal() {
		return checkVal;
	}
	public void setCheckVal(short checkVal) {
		this.checkVal = checkVal;
	}
	public short getLength() {
		return length;
	}
	public void setLength(short length) {
		this.length = length;
	}
	public short getMainCmd() {
		return mainCmd;
	}
	public void setMainCmd(short mainCmd) {
		this.mainCmd = mainCmd;
	}
	public short getSubCmd() {
		return subCmd;
	}
	public void setSubCmd(short subCmd) {
		this.subCmd = subCmd;
	}
	public boolean isCompressed() {
		return compressed;
	}
	public void setCompressed(boolean compressed) {
		this.compressed = compressed;
	}
	public long getSerialization1() {
		return serialization1;
	}

	public void setSerialization1(long serialization1) {
		this.serialization1 = serialization1;
	}

	public long getSerialization2() {
		return serialization2;
	}

	public void setSerialization2(long serialization2) {
		this.serialization2 = serialization2;
	}

	
}
