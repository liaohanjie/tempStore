package com.ks.account.service.impl;

import java.util.List;
import com.ks.account.service.BaseService;
import com.ks.account.service.ViolationLoggerService;
import com.ks.model.filter.ViolationLoggerFilter;
import com.ks.model.violation.ViolationLogger;

public class ViolationLoggerServiceImpl extends BaseService implements ViolationLoggerService {

	@Override
	public List<ViolationLogger> getViolationLoggers(ViolationLoggerFilter filter) {
		return violationLoggerDAO.getViolationLoggers(filter);
	}

	@Override
	public Integer getViolationLoggerCount(ViolationLoggerFilter filter) {
		return violationLoggerDAO.getViolationLoggerCount(filter);
	}

	@Override
	public void saveViolationLogger(ViolationLogger logger) {
		violationLoggerDAO.saveViolationLogger(logger);
	}

}
