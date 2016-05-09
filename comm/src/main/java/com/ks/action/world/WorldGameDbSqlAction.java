package com.ks.action.world;

import java.util.List;
import java.util.Map;

public interface WorldGameDbSqlAction {
	
	/**
	 * 查询结果集，并返回 List 对象
	 * 
	 * @param sql
	 * @return
	 */
	List<Map<String, Object>> querySqlForListMap(String sql);
	
	/**
	 * 查询结果对象，并返回 Map 对象
	 * 
	 * @param sql
	 * @return
	 */
	Map<String, Object> querySqlForMap(String sql);
}
