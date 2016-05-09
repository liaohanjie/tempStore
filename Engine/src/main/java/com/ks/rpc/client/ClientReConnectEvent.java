package com.ks.rpc.client;

import com.ks.event.GameEvent;

public class ClientReConnectEvent extends GameEvent {
	
	private RPCClient client;
	
	private RPCClientChannelHandler handler;
	public ClientReConnectEvent(RPCClient client,RPCClientChannelHandler handler) {
		super();
		this.client = client;
		this.handler = handler;
	}


	@Override
	public void runEvent()  throws Exception{
		client.reconnect(handler);
	}

}
