package com.ks.access;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DBBeanSetSqlSetList {
	DBBeanSetSelectSqlSet[] selects() default {};
	DBBeanSetDeleteSqlSet[] deletes() default {};
	DBBeanSetUpdateSqlSet[] updates() default {};
	DBBeanSetInsertSqlSet[] inserts() default {};
}
