package com.ks.logic.event;

import java.util.Date;

import org.apache.log4j.Logger;

import com.ks.event.GameEvent;
import com.ks.logger.LoggerFactory;
import com.sf.data.DataSDK;
import com.sf.data.domain.PayOrderInfo;

public class DataOrderEvent extends GameEvent {

	private Logger logger = LoggerFactory.get(DataOrderEvent.class);

	private PayOrderInfo info;

	public DataOrderEvent(int roleId, int gameId, String serverId, String orderNo, int amount, int gameCoin, String username) {
		this.info = new PayOrderInfo();
		this.info.setServerId(serverId);
		this.info.setGameId(gameId);
		this.info.setOrderNo(orderNo);
		this.info.setMoney(amount);
		this.info.setGameCoin(gameCoin);
		this.info.setUsername(username);
		this.info.setRoleId(roleId);
		this.info.setDealTime(new Date());
		this.info.setCreateTime(new Date());
	}

	@Override
	public void runEvent() {
		try {
			DataSDK.addOrder(info);
		} catch (Exception e) {
			logger.error("", e);
		}
	}

}
