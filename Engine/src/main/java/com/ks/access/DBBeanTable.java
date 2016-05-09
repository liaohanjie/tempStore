package com.ks.access;

import com.ks.access.mapper.RowMapper;

/**
 * DB Bean映射
 * @author Administrator
 *
 */
public abstract class DBBeanTable <T>{
	protected RowMapper<T> mapper;
	public abstract Object getDBFieldValue(T obj, String fname);
	public abstract Class getClazz();
	
	public <T>RowMapper<T> getRowMapper(){
		return (RowMapper<T>)mapper;
	}
}
