package com.ks.action.logic;

import java.util.List;
import java.util.Map;


/**
 * SQL 工具 Action
 * 
 * @date 2015年7月2日
 */
public interface SqlAction {
	
	/**
	 * 查询结果集，并返回 List 对象
	 * 
	 * @param sql
	 * @param args
	 * @return
	 */
	List<Map<String, Object>> querySqlForListMap(String sql, Object... args);
	
	/**
	 * 查询结果对象，并返回 Map 对象
	 * 
	 * @param sql
	 * @param args
	 * @return
	 */
	Map<String, Object> querySqlForMap(String sql, Object... args);
}
