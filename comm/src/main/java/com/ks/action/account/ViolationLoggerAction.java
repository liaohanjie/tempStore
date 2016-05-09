package com.ks.action.account;

import java.util.List;
import com.ks.model.filter.ViolationLoggerFilter;
import com.ks.model.violation.ViolationLogger;

/**
 * 违规日志
 * 
 * @author lipp 2016年1月23日
 */
public interface ViolationLoggerAction {
	/**
	 * 查看违规日志
	 * 
	 * @return
	 */
	List<ViolationLogger> getViolationLoggers(ViolationLoggerFilter filter);

	/**
	 * 得到违规日志总数量
	 * 
	 * @param filter
	 * @return
	 */
	Integer getViolationLoggerCount(ViolationLoggerFilter filter);

	/**
	 * 保存违规记录
	 */
	void saveViolationLogger(ViolationLogger logger);
}
