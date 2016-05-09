package com.ks.logic.service.impl;

import com.ks.action.world.WorldChatAction;
import com.ks.app.Application;
import com.ks.logic.service.BaseService;
import com.ks.logic.service.MarqueeService;
import com.ks.model.chat.MarqueeMsg;
import com.ks.rpc.RPCKernel;

/**
 * 跑马灯 Service
 * 
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2016年2月29日
 */
public class MarqueeServiceImpl extends BaseService implements MarqueeService {

	@Override
	public void add(MarqueeMsg entity) {
		WorldChatAction action = RPCKernel.getRemoteByServerType(Application.WORLD_SERVER, WorldChatAction.class);
		action.addMarquee(entity);
	}
}
