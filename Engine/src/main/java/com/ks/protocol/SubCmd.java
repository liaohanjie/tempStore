package com.ks.protocol;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 子命令
 * 注释在方法上面
 * @author ks
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface SubCmd {
	/**
	 * 子命令
	 * @return 子命令
	 */
	short subCmd();
	
	/**
	 * 参数
	 * 可选配置，基本数据类型填写基本类型如：int,boolean,long,float 等，
	 * vo类型填写vo编号 vo编号参见{@link com.ks.protocol.Message}说明，
	 * 如果客户端传来的是数组类型，就在参数后面加下划线true如：int_true,
	 * boolean_true,user_true。不填下划线等价于int_false,boolean_false,
	 * user_false。
	 * @return 参数
	 */
	String[] args() default {};
}
