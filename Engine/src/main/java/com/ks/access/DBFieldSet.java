package com.ks.access;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface DBFieldSet {
	String dbfname();
	String parseJavaField() default "";
	String parseDBField() default "";
}
