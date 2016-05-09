package com.ks.access;

import java.util.List;

/**
 * DBBean sql 配置
 * @author zck
 *
 */
public class DBBeanSqlSet {
	private String sql;
	private List<Object> params;
	
	public DBBeanSqlSet(String sql, List<Object> params) {
		super();
		this.sql = sql;
		this.params = params;
	}
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
	public List<Object> getParams() {
		return params;
	}
	public void setParams(List<Object> params) {
		this.params = params;
	}
}
