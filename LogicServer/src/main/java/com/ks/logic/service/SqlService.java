package com.ks.logic.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;


/**
 * 直接执行SQL语句，慎用
 * 
 * @author zhoujf
 */
public interface SqlService {
	
	/**
	 * 查询结果集，并返回 List<Map> 对象
	 * 
	 * @param sql
	 * @param args
	 * @return
	 * @throws SQLException
	 */
	List<Map<String, Object>> querySqlForListMap(String sql, Object...args) throws SQLException;
	
	/**
	 * 查询结果对象，并返回 Map 对象
	 * 
	 * @param sql
	 * @param args
	 * @return
	 * @throws SQLException
	 */
	Map<String, Object> querySqlForMap(String sql, Object...args) throws SQLException;
}