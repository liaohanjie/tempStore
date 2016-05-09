package com.ks.protocol;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 
 * 注释在字段名上面
 * @author ks
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldDesc {
	String desc();
	String rename() default "";
}
