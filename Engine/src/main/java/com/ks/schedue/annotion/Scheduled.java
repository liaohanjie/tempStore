package com.ks.schedue.annotion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * 定时任务注解
 * @author hanjie.l
 * <ul>
 * <li>【* * * * * *】分别代表  【秒  分  时  日  月  年  礼拜几】</li>
 * 
 * <li>【*\\/10 * * * * *】 每10秒执行一次,\\只是注释里的转义符</li>
 * 
 * <li>【0 0 8-10 * * *】 每天8:00,9:00,10:00执行一次</li>
 * 
 * <li>【0 0/30 8-10 * * *】 每天8:00, 8:30, 9:00, 9:30, 10:00执行，注意10：30不执行</li>
 * 
 * <li>【0 0 9-17 * * MON-FRI】周一到周五的9到17点，每个整点执行一次</li>
 * </ul>
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Scheduled {

	/** 任务名 */
	public String name();

	/** 表达式值 */
	public String value();
	
	/**是否为测试*/
	public boolean debug() default false;
}