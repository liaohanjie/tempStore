package com.ks.cache;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface JedisFieldSet {
	String jedisname() default "";
	String parseJavaField() default "";
	String parseJedisField() default "";
}
