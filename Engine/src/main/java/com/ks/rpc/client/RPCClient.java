package com.ks.rpc.client;

import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;

import com.ks.rpc.RPCComm;
import com.ks.timer.TimerController;

/**
 * 远程调用客户端
 * @author ks
 */
public class RPCClient {
	private SocketAddress address;
	/**服务器id*/
	private String serverId;
	/**服务器类型*/
	private int serverType;
	/**客户端*/
	private List<RPCClientChannelHandler> handlers;
	/**下标*/
	private int index = 0;
	public RPCClient(SocketAddress address, String serverId, int serverType) {
		super();
		this.address = address;
		this.serverId = serverId;
		this.serverType = serverType;
	}
	
	public boolean canUse() throws Exception{
		if(handlers == null){
			init();
		}
		for(RPCClientChannelHandler handler : handlers){
			if(handler.canUse()){
				return true;
			}
		}
		return false;
	}
	
	public synchronized void init() throws Exception{
		if(handlers == null){
			handlers = new ArrayList<RPCClientChannelHandler>();
			for(int i=0;i<Runtime.getRuntime().availableProcessors()*2;i++){
				handlers.add(RPCClientBootstrap.connect(this.getAddress(), this.getServerId(),this.getServerType()));
			}
		}
	}
	
	public RPCClientChannelHandler reconnect(RPCClientChannelHandler handler) throws Exception{
		synchronized (handler) {
			if(handlers.indexOf(handler)== -1){
				return handler;
			}
			RPCClientChannelHandler hand = RPCClientBootstrap.connect(this.getAddress(), this.getServerId(),this.getServerType());
			handlers.set(handlers.indexOf(handler), hand);
			return hand;
		}
	}
	
	public void reconnectAll() {
		for(RPCClientChannelHandler handler : handlers){
			TimerController.execEvent(new ClientReConnectEvent(this, handler));
		}
	}
	
	private synchronized RPCClientChannelHandler getHandler() throws Exception{
		return getHandler(0);
	}
	
	private RPCClientChannelHandler getHandler(int count) throws Exception{
		RPCClientChannelHandler handler = handlers.get(index);
		index++;
		index = index >= handlers.size() ? 0 : index;
		if(!handler.canUse()){
			if(!this.canUse()){
				reconnectAll();
				throw new Exception("no node server ID : "+serverId);
			}
			RPCClientChannelHandler h = reconnect(handler);
			if(h.canUse()){
				return h;
			}
			count+=1;
			if(count == handlers.size()){
				throw new Exception("no node server ID : "+serverId);
			}
			return getHandler(count);
		}
		return handler;
	}
	
	public void sendMessage(RPCComm comm) throws Exception{
		if(handlers == null){
			init();
		}
		RPCClientChannelHandler handler = getHandler();
		handler.sendMessage(comm);
	}

	public String getServerId() {
		return serverId;
	}

	public void setServerId(String serverId) {
		this.serverId = serverId;
	}

	public int getServerType() {
		return serverType;
	}

	public void setServerType(int serverType) {
		this.serverType = serverType;
	}

	public SocketAddress getAddress() {
		return address;
	}

	public void setAddress(SocketAddress address) {
		this.address = address;
	}

}
