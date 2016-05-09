package com.ks.logic.service.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ks.logic.service.BaseService;
import com.ks.logic.service.SqlService;

/**
 * 直接执行SQL语句
 * 
 * @author zhoujf
 */
public final class SqlServiceImpl extends BaseService implements SqlService {

	@Override
    public List<Map<String, Object>> querySqlForListMap(String sql, Object... args) throws SQLException {
		return sqlDAO.querySqlForListMap(sql, args);
    }

	@Override
    public Map<String, Object> querySqlForMap(String sql, Object... args) throws SQLException {
		List<Map<String, Object>> list = sqlDAO.querySqlForListMap(sql, args);
		if(list != null && !list.isEmpty()) {
			return list.get(0);
		}
	    return null;
    }

}