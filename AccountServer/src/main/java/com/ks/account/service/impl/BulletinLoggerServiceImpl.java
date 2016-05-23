package com.ks.account.service.impl;

import java.util.List;

import com.ks.account.service.BaseService;
import com.ks.account.service.BulletinLoggerService;
import com.ks.model.notice.BulletinLogger;

public class BulletinLoggerServiceImpl extends BaseService implements BulletinLoggerService {

	@Override
	public void saveLogger(BulletinLogger logger) {
		bulletinLoggerDAO.saveLogger(logger);
	}

	@Override
	public List<BulletinLogger> getBulletinLogger() {
		return bulletinLoggerDAO.getBulletinLogger();
	}

	@Override
	public Integer getBulletinLogCount() {
		return bulletinLoggerDAO.getBulletinLoggerCount();
	}

}
