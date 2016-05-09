package com.ks.rpc;

import java.io.Serializable;
import java.util.Comparator;
/**
 * 服务器性息
 * @author ks
 *
 */
public final class ServerInfo implements Comparator<ServerInfo>,Serializable{
	private static final long serialVersionUID = -8964659401428742063L;
	/**服务器地址*/
	private String host;
	/**服务器端口*/
	private int port;
	/**服务器id*/
	private String serverId;
	/**服务器人数*/
	private int num=0;
	/**关闭数量*/
	private int closedCount;
	public ServerInfo(String host, int port, String serverId) {
		this.host = host;
		this.port = port;
		this.serverId = serverId;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getServerId() {
		return serverId;
	}

	public void setServerId(String serverId) {
		this.serverId = serverId;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}
	
	public int getClosedCount() {
		return closedCount;
	}

	public void setClosedCount(int closedCount) {
		this.closedCount = closedCount;
	}

	@Override
	public int compare(ServerInfo o1, ServerInfo o2) {
		return closedCount==Runtime.getRuntime().availableProcessors()*2?Integer.MAX_VALUE:o1.num-o2.num;
	}
	
}
