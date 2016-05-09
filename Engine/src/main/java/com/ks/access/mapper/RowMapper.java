package com.ks.access.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface RowMapper<T> {
	/**
	 * 返回单一结果
	 * */
	T rowMapper(ResultSet rs) throws SQLException;
}
