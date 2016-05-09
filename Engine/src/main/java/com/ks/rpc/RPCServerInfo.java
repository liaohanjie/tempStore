package com.ks.rpc;
/**
 * rpc服务器信息
 * @author ks.wu
 *
 */
public class RPCServerInfo {
	/**服务器端口*/
	private int port;
	/**服务器地址*/
	private String host;
	/**服务器类型*/
	private int serverType;
	/**服务器编号*/
	private String serverId;
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public int getServerType() {
		return serverType;
	}
	public void setServerType(int serverType) {
		this.serverType = serverType;
	}
	public String getServerId() {
		return serverId;
	}
	public void setServerId(String serverId) {
		this.serverId = serverId;
	}
}
