package com.ks.access;

import java.util.HashMap;
import java.util.Map;

import com.ks.access.mapper.RowMapper;

/**
 * 
 * db bean 模版
 * @author zck
 *
 * @param <T>
 */
public abstract class DBBeanSqlTemplate<T> {
	protected Class clazz;

	protected Map<String, String> sqlMap;
	protected Map<String, DBBeanParamMethod<T>> paramMethodMap;
	protected RowMapper<T> mapper;

	public DBBeanSqlTemplate() {
		super();
		this.sqlMap = new HashMap<String, String>();
		this.paramMethodMap = new HashMap<String, DBBeanParamMethod<T>>();
	}
	
	public Map<String, String> getSqlMap() {
		return sqlMap;
	}
	public void setSqlMap(Map<String, String> sqlMap) {
		this.sqlMap = sqlMap;
	}
	public Map<String, DBBeanParamMethod<T>> getParamMethodMap() {
		return paramMethodMap;
	}
	public void setParamMethodMap(Map<String, DBBeanParamMethod<T>> paramMethodMap) {
		this.paramMethodMap = paramMethodMap;
	}
	
	public String getSql(String key){
		return sqlMap.get(key);
	}
	
	public DBBeanParamMethod<T> getParamMethod(String key){
		return paramMethodMap.get(key);
	}

	public Class<T> getClazz() {
		return clazz;
	}

	public void setClazz(Class<T> clazz) {
		this.clazz = clazz;
	}
	
	public RowMapper<T> getMapper() {
		return mapper;
	}

	public void setMapper(RowMapper<T> mapper) {
		this.mapper = mapper;
	}
	
}
