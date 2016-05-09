package com.ks.access;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface DBBeanSet {
	/**
	 * tablename不配置表示tablename动态生成
	 * @return
	 */
	String tablename_prefix() default "";
	String createtablename() default "public static String getTableName(int index){return TABLE_NAME_PREFIX + (index % 10);}";
	String tablename() default "";
	String[] imports() default {};
	String[] primaryKey() default {};
	boolean createUpdateSql() default true;
	boolean createInsertSql() default true;
	boolean createDeleteSql() default true;
	boolean createSelectSql() default true;
}
