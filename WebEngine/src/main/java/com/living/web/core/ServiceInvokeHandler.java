package com.living.web.core;

import java.lang.reflect.Method;

import com.ks.rpc.client.ClientRPCHandler;

public class ServiceInvokeHandler extends ClientRPCHandler {

	public ServiceInvokeHandler(String name, int flag, int type, String serverId) {
		super(name, flag, type, serverId);
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		return super.invoke(proxy, method, args);
	}
	
}
