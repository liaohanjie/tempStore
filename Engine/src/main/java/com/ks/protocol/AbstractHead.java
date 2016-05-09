package com.ks.protocol;

public class AbstractHead extends Message {

	private static final long serialVersionUID = 1L;
	/**数据区长度*/
	private short length;
	/**主命令*/
	private short mainCmd;
	/**子命令*/
	private short subCmd;
	/**是否被压缩*/
	private boolean compressed;
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
	public short getLength() {
		return length;
	}
	public void setLength(short length) {
		this.length = length;
	}
	public boolean isCompressed() {
		return compressed;
	}
	public void setCompressed(boolean compressed) {
		this.compressed = compressed;
	}
}
