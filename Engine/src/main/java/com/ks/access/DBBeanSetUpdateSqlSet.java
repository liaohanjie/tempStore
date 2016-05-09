package com.ks.access;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DBBeanSetUpdateSqlSet {
	String name();
	Class<?> dbbean();
	/**动态fields*/
	boolean auto_fields() default false;
	String[] fields() default {};
	String[] wheres() default {};
}
