package com.ks.logic.event;

import java.util.Date;

import org.apache.log4j.Logger;

import com.ks.event.GameEvent;
import com.ks.logger.LoggerFactory;
import com.sf.data.DataSDK;

public class DataOnlineEvent extends GameEvent {

	private Logger logger = LoggerFactory.get(DataOnlineEvent.class);

	private int num;
	private String serverId;

	public DataOnlineEvent(String serverId, int num) {
		this.num = num;
		this.serverId = serverId;
	}

	@Override
	public void runEvent() {
		try {
			DataSDK.serverOnline(1, serverId, num, new Date());
		} catch (Exception e) {
			logger.error("", e);
		}
	}

}
