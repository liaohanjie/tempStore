package com.ks.model.logger;

public class SweepCountLogger extends GameLogger {
	/**
	 * @author fengpeng E-mail:fengpeng_15@163.com
	 * 
	 * @version 创建时间：2014年7月30日 下午5:21:21
	 */
	private static final long serialVersionUID = 1L;
//	public static final int TYPE_购买扫荡次数 = 1;
//	public static final int TYPE_消耗扫荡次数= 2;
	
	public static SweepCountLogger createSweepCountLogger(int userId,int type,int num,int assId,String description){
		SweepCountLogger logger = new SweepCountLogger();
		logger.setLoggerType(GameLogger.LOGGER_TYPE_SWEEP_COUNT);
		logger.setUserId(userId);
		logger.setType(type);
		logger.setNum(num);
		logger.setAssId(assId);
		logger.setDescription(description);
		return logger;
	}
}
