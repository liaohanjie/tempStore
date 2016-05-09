package com.ks.logic.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ks.access.GameDAOTemplate;

/**
 * SQL 操作
 * 
 * @author zhoujf
 */
public class SqlDAO extends GameDAOTemplate {
	
	/**
	 * 执行查询SQL语句
	 * 
	 * @param sql
	 * @param args
	 * @return
	 * @throws SQLException
	 */
	public List<Map<String, Object>> querySqlForListMap(String sql, Object...args) throws SQLException{
		return queryForListMap(sql, args);
	}
}
