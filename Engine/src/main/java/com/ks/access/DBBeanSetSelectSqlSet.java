package com.ks.access;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DBBeanSetSelectSqlSet {
	String name();
	Class<?> dbbean();
	String[] fields() default {};
	String[] wheres() default {};
	String[] limit() default {};
	String oderby() default "";
	boolean forupdate() default false;
}
