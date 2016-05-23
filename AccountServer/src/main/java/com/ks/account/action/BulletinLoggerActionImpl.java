package com.ks.account.action;

import java.util.List;

import com.ks.account.service.BulletinLoggerService;
import com.ks.account.service.ServiceFactory;
import com.ks.action.account.BulletinLoggerAction;
import com.ks.model.notice.BulletinLogger;

public class BulletinLoggerActionImpl implements BulletinLoggerAction {

	private static final BulletinLoggerService bulletinLoggerService = ServiceFactory.getService(BulletinLoggerService.class);

	@Override
	public void saveLogger(BulletinLogger logger) {
		bulletinLoggerService.saveLogger(logger);
	}

	@Override
	public List<BulletinLogger> getBulletinLogger() {
		return bulletinLoggerService.getBulletinLogger();
	}

	@Override
	public Integer getBulletinLogCount() {
		return bulletinLoggerService.getBulletinLogCount();
	}

}
