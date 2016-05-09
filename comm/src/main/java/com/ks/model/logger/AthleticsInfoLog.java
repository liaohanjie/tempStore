package com.ks.model.logger;

/**
 * @author fengpeng E-mail:fengpeng_15@163.com
 * @version 创建时间：2014年7月25日 上午10:03:14
 * pvp 竞技点日志
 */
public class AthleticsInfoLog extends GameLogger{
	private static final long serialVersionUID = 1L;
	public static int category_竞技分数=1;
	public static int category_竞技点=2;
//	public static int 竞技点_type_自动回点=1;
//	public static int 竞技点_type_比赛扣点=2;
//	public static int 竞技点_type_花钱买点=3;
//	public static int 竞技点_type_第一次送竞技点=4;
//	public static int 竞技分数_type_胜负奖励=1;
	
	public static AthleticsInfoLog createBakAthleticsInfoLog(int loggerType,int userId,int type,int num,int assId,String des){
		AthleticsInfoLog logger = new AthleticsInfoLog();
		logger.setLoggerType(loggerType);
		logger.setUserId(userId);
		logger.setType(type);
		logger.setNum(num);
		logger.setAssId(assId);
		logger.setDescription(des);
		return logger;
	}
}
