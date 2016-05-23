package com.ks.account.action;

import java.util.List;
import com.ks.account.service.ServiceFactory;
import com.ks.account.service.ViolationLoggerService;
import com.ks.action.account.ViolationLoggerAction;
import com.ks.model.filter.ViolationLoggerFilter;
import com.ks.model.violation.ViolationLogger;

public class ViolationLoggerActionImpl implements ViolationLoggerAction {
	private static final ViolationLoggerService violationLoggerService = ServiceFactory.getService(ViolationLoggerService.class);

	@Override
	public List<ViolationLogger> getViolationLoggers(ViolationLoggerFilter filter) {
		return violationLoggerService.getViolationLoggers(filter);
	}

	@Override
	public Integer getViolationLoggerCount(ViolationLoggerFilter filter) {
		return violationLoggerService.getViolationLoggerCount(filter);
	}

	@Override
	public void saveViolationLogger(ViolationLogger logger) {
		violationLoggerService.saveViolationLogger(logger);
	}

}
