package com.ks.access;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 动态配置数据
 * @author zck
 *
 */
public class GameDynamicDaoTemplate extends GameDAOTemplate {
	/**
	 * 获取连接
	 * @return
	 * @throws SQLException
	 */
	protected Connection getConnection() throws SQLException{
		return DataSourceUtils.getDynamicConnection();
	}
}
