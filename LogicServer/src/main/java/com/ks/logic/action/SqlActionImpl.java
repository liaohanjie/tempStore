package com.ks.logic.action;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ks.action.logic.SqlAction;
import com.ks.logic.service.ServiceFactory;
import com.ks.logic.service.SqlService;

/**
 * SQL 工具 Action
 * 
 * @author zhoujf
 */
public class SqlActionImpl implements SqlAction {
	
	private static final SqlService sqlService = ServiceFactory.getService(SqlService.class);

	@Override
    public List<Map<String, Object>> querySqlForListMap(String sql, Object... args) {
	    try {
	        return sqlService.querySqlForListMap(sql, args);
        } catch (SQLException e) {
	        e.printStackTrace();
	        throw new RuntimeException(e.getMessage());
        }
    }

	@Override
    public Map<String, Object> querySqlForMap(String sql, Object... args) {
	    try {
	        return sqlService.querySqlForMap(sql, args);
        } catch (SQLException e) {
	        e.printStackTrace();
	        throw new RuntimeException(e.getMessage());
        }
    }
}
