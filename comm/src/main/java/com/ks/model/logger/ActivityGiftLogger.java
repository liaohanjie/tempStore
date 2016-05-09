package com.ks.model.logger;

/**
 * 活动礼包领取日志
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2015年7月13日
 */
public class ActivityGiftLogger extends GameLogger {

	private static final long serialVersionUID = 1L;
	
	
	public static ActivityGiftLogger createActivityGiftLogger(int userId,int type,int num,int assId,String description){
		ActivityGiftLogger logger = new ActivityGiftLogger();
		logger.setLoggerType(GameLogger.LOGGER_TYPE_GOLD);
		logger.setUserId(userId);
		logger.setType(type);
		logger.setNum(num);
		logger.setAssId(assId);
		logger.setDescription(description);
		return logger;
	}
}
