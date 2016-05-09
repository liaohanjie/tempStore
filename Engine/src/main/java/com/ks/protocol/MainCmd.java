package com.ks.protocol;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * 主命令
 * 注释在类上面
 * @author ks
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface MainCmd {
	/**
	 * 主命令
	 * @return 主命令
	 */
	short mainCmd();
}
