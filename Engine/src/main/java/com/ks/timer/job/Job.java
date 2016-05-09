package com.ks.timer.job;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * JOB
 * @author ks
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Job {
	/**
	 * job运行规则 : hh:mm,hh:mm?week,week
	 * @return job运行规则
	 */
	String context();
}
